package com.msr.cg.afrimeta.magasin.dto;

import jakarta.validation.constraints.NotNull;

public record MagasinDto(
        Long magasinId,
        String libele,
        String logoUrl,
        String description,
        @NotNull
        int userId
) {
}
