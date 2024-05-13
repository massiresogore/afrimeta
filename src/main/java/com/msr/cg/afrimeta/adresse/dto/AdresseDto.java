package com.msr.cg.afrimeta.adresse.dto;


import jakarta.validation.constraints.NotNull;

public record AdresseDto(
        @NotNull
        Long adresseId,
        @NotNull
        String numero,
        int cp,
        Long villeId
) {
}
