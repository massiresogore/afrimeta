package com.msr.cg.afrimeta.profile.dto;

import com.msr.cg.afrimeta.profile.GenreEnum;

import java.util.Date;

public record ProfileDto(
     Long profileId,
     String nom,
     String prenom,
     String numeroTelephone,
     Date dateNaissance,
     GenreEnum genre,
     String addresse,
     String ville,
     String codePostal,
     String pays,
     String profilePicturePrl,
     String bio
) {
}
