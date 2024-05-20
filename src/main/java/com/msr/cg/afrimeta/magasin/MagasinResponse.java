package com.msr.cg.afrimeta.magasin;

import com.msr.cg.afrimeta.clientUser.ClientUser;

public record MagasinResponse(
        Long magasinId,
        String libele,
        String description,
        String logo,
        Long clientUser
) {
}
