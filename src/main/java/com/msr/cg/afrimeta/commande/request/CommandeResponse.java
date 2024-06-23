package com.msr.cg.afrimeta.commande.request;

import com.msr.cg.afrimeta.produit.dto.dto.ProduitResponse;

import java.util.List;

public record CommandeResponse(
        Long commandeId,
        String address,
        List<ProduitResponse> produitResponses,
        String createdAt,
        String name,
        int numItemsInCart,
        String orderTotal,
        String publishedAt,
        String updatedAt
) {
}

