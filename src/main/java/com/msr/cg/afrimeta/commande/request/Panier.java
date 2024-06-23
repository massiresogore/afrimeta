package com.msr.cg.afrimeta.commande.request;

import jakarta.validation.constraints.NotNull;

public record Panier(
        @NotNull
        String produitId,
        @NotNull
        String quantity,
        String couleurName

) {
}
