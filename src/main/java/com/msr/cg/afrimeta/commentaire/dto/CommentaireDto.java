package com.msr.cg.afrimeta.commentaire.dto;

import com.msr.cg.afrimeta.clientUser.dto.ClientUserDto;
import jakarta.validation.constraints.NotNull;

public record CommentaireDto(
        Long commentaireId,
        String commentaireDate,

        @NotNull
        String contenu,

        com.msr.cg.afrimeta.produit.dto.dto.ProduitResponse produitDto,

        ClientUserDto clientUserDto
) {
}
