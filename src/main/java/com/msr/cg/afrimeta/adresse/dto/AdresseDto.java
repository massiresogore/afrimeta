package com.msr.cg.afrimeta.adresse.dto;


import com.msr.cg.afrimeta.ville.Ville;
import jakarta.validation.constraints.NotNull;

public record AdresseDto(
        @NotNull
        Long adresseId,
        @NotNull
        String numero,
        int cp,
        Ville ville
) {
}
