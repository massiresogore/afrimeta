package com.msr.cg.afrimeta.magasin.dto;

import java.util.Map;

public record MagasinResponse(
        Long magasinId,
        String libele,
        String description,
        Map<String,String> logo,
        Long clientUser
) {
}
