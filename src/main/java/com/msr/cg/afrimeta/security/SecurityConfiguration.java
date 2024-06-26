package com.msr.cg.afrimeta.security;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
public class SecurityConfiguration {
    private final RSAPublicKey rsaPublicKey;
    private final RSAPrivateKey rsaPrivateKey;
    private final CustomBasicAuthenticationEntryPoint customBasicAuthenticationEntryPoint;
    private final CustomBearerTokenAccessDeniedHandler customBearerTokenAccessDeniedHandler;

    private final CustomBearerTokenAuthenticationEntryPoint customBearerTokenAuthenticationEntryPoint;



    @Value("${api.endpoint.base-url}")
    private String baseUrl;

    public SecurityConfiguration(CustomBasicAuthenticationEntryPoint customBasicAuthenticationEntryPoint,
                                 CustomBearerTokenAccessDeniedHandler customBearerTokenAccessDeniedHandler,
                                 CustomBearerTokenAuthenticationEntryPoint customBearerTokenAuthenticationEntryPoint) throws NoSuchAlgorithmException {
        this.customBasicAuthenticationEntryPoint = customBasicAuthenticationEntryPoint;
        this.customBearerTokenAccessDeniedHandler = customBearerTokenAccessDeniedHandler;
        this.customBearerTokenAuthenticationEntryPoint = customBearerTokenAuthenticationEntryPoint;
        //Generate Public/Private key pair in java
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);//the generate key have a size of 2048 bits
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        this.rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        this.rsaPublicKey =(RSAPublicKey) keyPair.getPublic();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(
                authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                        .requestMatchers(HttpMethod.GET,this.baseUrl+"/bataclan/images").permitAll()
                        /***********Produits**********/
                        .requestMatchers(HttpMethod.GET,this.baseUrl+"produits/bataclan").permitAll()
                        .requestMatchers(HttpMethod.GET,this.baseUrl+"/produits/**").permitAll()
                        .requestMatchers(HttpMethod.GET,this.baseUrl+"/bataclan/images/files/**").permitAll()
                        .requestMatchers(HttpMethod.POST,this.baseUrl+"/produits/**").hasAnyAuthority("ROLE_user","ROLE_admin")
                        /***********Produits**********/

                        /***********Commandes**********/
//                        .requestMatchers(HttpMethod.POST,this.baseUrl+"/commandes/client/**").hasAnyAuthority("ROLE_user","ROLE_admin")//désactive pour le test
                        .requestMatchers(HttpMethod.POST,this.baseUrl+"/commandes/client/**").permitAll()//active pou le test
//                        .requestMatchers(HttpMethod.GET,this.baseUrl+"/commandes/**").hasAnyAuthority("ROLE_user","ROLE_admin")//désactive pour le test
                        .requestMatchers(HttpMethod.GET,this.baseUrl+"/commandes/**").permitAll()//active pou le test
                        .requestMatchers(HttpMethod.GET,this.baseUrl+"/commandes").hasAnyAuthority("ROLE_user","ROLE_admin")
                        /***********Commandes**********/

                        /***********Website**********/
                        .requestMatchers(HttpMethod.POST,this.baseUrl+"/website/**").hasAnyAuthority("ROLE_user","ROLE_admin")
                        //.requestMatchers(HttpMethod.GET,this.baseUrl+"/commandes/**").hasAnyAuthority("ROLE_user","ROLE_admin")//désactive pour le test
                        //.requestMatchers(HttpMethod.GET,this.baseUrl+"/commandes").hasAnyAuthority("ROLE_user","ROLE_admin")
                        /***********Website**********/

                        /*********** User **********/
                        .requestMatchers(HttpMethod.GET,this.baseUrl+"/users").hasAnyAuthority("ROLE_admin","ROLE_super")
                        .requestMatchers(HttpMethod.POST,this.baseUrl+"/users").hasAnyAuthority("ROLE_admin","ROLE_super")
                        .requestMatchers(HttpMethod.PATCH,this.baseUrl+"/users/**").hasAnyAuthority("ROLE_admin","ROLE_super")
                        .requestMatchers(HttpMethod.DELETE,this.baseUrl+"/users/**").hasAnyAuthority("ROLE_admin","ROLE_super")
                        /*********** End User **********/

                        .requestMatchers(HttpMethod.GET,this.baseUrl+"/categories").permitAll()
                        .requestMatchers(HttpMethod.POST,this.baseUrl+"/categories").hasAuthority("ROLE_user") // etape 1
                        .requestMatchers(HttpMethod.PATCH,this.baseUrl+"/categories/**").hasAuthority("ROLE_user")
                        .requestMatchers(HttpMethod.DELETE,this.baseUrl+"/categories/**").hasAuthority("ROLE_user")

                        .requestMatchers(HttpMethod.GET,this.baseUrl+"/magasins/**").permitAll()
                        .requestMatchers(HttpMethod.POST,this.baseUrl+"/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST,this.baseUrl+"/magasins/**").hasAnyAuthority("ROLE_user","ROLE_admin") //magasin de user ** ,etape 2
                        .requestMatchers(HttpMethod.POST,this.baseUrl+"/website/**").hasAuthority("ROLE_user") //website de magasin ** etape 3
                        .requestMatchers(HttpMethod.POST,this.baseUrl+"/commandes/client/**").hasAuthority("ROLE_user") //commande de client ** etape5
                        //desauthorise les connexion non securise
                        .anyRequest()
                        .authenticated())

                //.headers(AbstractHttpConfigurer::disable)
                //.headers(AbstractHttpConfigurer::disable)//autorise h2-console
                .csrf(AbstractHttpConfigurer::disable)//
                //.cors(Customizer.withDefaults())
                //ceci permet dintercepter les erreur dautentification et d'autorisation
                .httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(this.customBasicAuthenticationEntryPoint))
                //.httpBasic(Customizer.withDefaults())
                .oauth2ResourceServer(Customizer.withDefaults())// permet à spring de vérifier l'autenticité du token envoyé
                //ceci intercept les erreur de token
                .oauth2ResourceServer(oauth2ResourceServer-> oauth2ResourceServer
                        .jwt(Customizer.withDefaults())
                        .authenticationEntryPoint(this.customBearerTokenAuthenticationEntryPoint)
                        .accessDeniedHandler(this.customBearerTokenAccessDeniedHandler)
                )
                //nous devons désactivé la session pour  sautentification et l'autorisation de jwt
                //desactive the session for any request
                .sessionManagement(sessionManagement->sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))//si nou
                .build();
        //spring va nous aider vérifier l'authenticité du jeton JWT soumis
        //maintenant que nous utilison jwt pour l'authorisation et l'authentification
        //On doit désactivé la session
        //Ligne 74 dit a spring qu'il ne conserve pas de session
        // cela peut économiner beaucoup de ram lorsquil ya plusieurs utilisateur connecté

    }


    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder(12);
    }


    /**
     * on met @Bean pour lui permetre de l'injecter dans
     * JwtProvider
     *
     * @return
     */
    @Bean
    public JwtEncoder jwtEncoder()
    {
        JWK jwk = new RSAKey.Builder(this.rsaPublicKey).privateKey(this.rsaPrivateKey).build();
        JWKSource<SecurityContext> jwkSet = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSet);
    }

    @Bean
    public JwtDecoder jwtDecoder()
    {
        return NimbusJwtDecoder.withPublicKey(this.rsaPublicKey).build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter()
    {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");/*vide parce que le default est 'SCOPE_
        sinon on aurait SCOPE_ROLE_admin
        '*/

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
        /*nous renvoyon le nouveau converter apres avoir subit des modification*/
    }
    /*
     * authentication converter && jwtDecoder && ligne 69, 70 sont utilisé pour s'autentifier et être authorisé
     * */


}
