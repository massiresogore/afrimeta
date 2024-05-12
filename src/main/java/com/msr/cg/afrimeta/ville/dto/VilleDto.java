package com.msr.cg.afrimeta.ville.dto;

import jakarta.validation.constraints.NotNull;

public record VilleDto (
    @NotNull
    Long villeId,
    @NotNull
    String nom
){
}
