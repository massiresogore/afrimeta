package com.msr.cg.afrimeta.magasin.dto;

public record MagasinResponse(
        Long magasinId,
        String libele,
        String description,
        String logo,
        Long clientUser
) {
}
