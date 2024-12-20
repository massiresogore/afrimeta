package com.msr.cg.afrimeta.magasin.dto;

import com.msr.cg.afrimeta.clientUser.ClientUser;

import java.util.Map;

public record MagasinDto(
        Long magasinId,
        String libele,
        String description,
        Map<String,String> logo,
        ClientUser clientUser
) {}
