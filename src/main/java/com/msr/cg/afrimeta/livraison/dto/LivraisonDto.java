package com.msr.cg.afrimeta.livraison.dto;

import com.msr.cg.afrimeta.commande.dto.CommandeDto;
import com.msr.cg.afrimeta.modelivraison.dto.ModeLivraisonDto;
import jakarta.validation.constraints.NotNull;

public record LivraisonDto(
        Long livraisonId,
        String paiementDate,

        @NotNull
        String description,

        ModeLivraisonDto modeLivraison,

        CommandeDto commande
) {
}
