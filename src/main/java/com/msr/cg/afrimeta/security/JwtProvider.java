package com.msr.cg.afrimeta.security;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class JwtProvider {


    private final JwtEncoder jwtEncoder;

    public JwtProvider(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }


    public String createToken(Authentication authentication) {
        /*nous créons ceci

        *
        {
           iss": "self",
           sub": "massire",
           exp": 1713714327,
           iat": 1713707127,
           authorities": "ROLE_admin"
         }
        * */
        Instant now = Instant.now();
        long expireIn = 2; //expire en 2 hours

        //Préparation de la révendication(claim) de l'authorisation
        //
        /**
         * on crée une collection de chaine de caractère pour
         * nous retourner une collection de roles sépare par un
         * espace "Role_admin ROLE_user", l'inverse de
         * ce qu'on créé dans MysPrincipal
         *
         * grantedAuthority.getAuthority(), retunr "ROLE_admin"
         * ou "ROLE_user"
         */
        //System.out.println(authentication.getAuthorities());
        String autorities = authentication.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.joining(" "));//Doit etre délimité par un space
       // System.out.println(autorities);
        //Si l'utilisateur à ces role: exp: role="user admin super",
        //MyUserPrincipal la transformé en [ROLE_user, ROLE_admin, ROLE_super]
        // depuit cette fonction
//        public Collection<? extends GrantedAuthority> getAuthorities() {
//            return Arrays.stream(StringUtils.tokenizeToStringArray(this.clientUser.getRole(), " "))
//                    .map(role-> new SimpleGrantedAuthority("ROLE_"+role)).toList();
//        }
        //Alors autorities est tranformé ici en  autorities= "ROLE_user ROLE_admin ROLE_super"
        // renvera "Role_admin ROLE_user"
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(expireIn, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("authorities", autorities)
                .build();

       //ceci  System.out.println(jwtClaimsSet.getClaims().entrySet()); renvoie
        /**
         * [
         * iss=self,
         * sub=massire,
         * exp=2024-06-28T23:38:15.857765Z,
         * iat=2024-06-28T21:38:15.857765Z,
         * authorities=ROLE_user ROLE_admin ROLE_super]
         */

        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
    }
}