package com.msr.cg.afrimeta.magasin.dto;

import java.util.Map;

public record MagasinResponse(
        Long magasinId,
        String libele,
        String description,
        LogoDto logo,
        Long clientUser
) {
}
