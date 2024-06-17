package com.msr.cg.afrimeta.security;


import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
        String autorities = authentication.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.joining(" "));//Doit etre délimité par un space
        // renvera "Role_admin ROLE_user"
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(expireIn, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("authorities", autorities)
                .build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
    }
}