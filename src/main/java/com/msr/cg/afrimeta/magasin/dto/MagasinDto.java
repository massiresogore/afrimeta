package com.msr.cg.afrimeta.magasin.dto;

import com.msr.cg.afrimeta.clientUser.ClientUser;

public record MagasinDto(
        Long magasinId,
        String libele,
        String description,
        String logo,
        ClientUser clientUser
) {}
