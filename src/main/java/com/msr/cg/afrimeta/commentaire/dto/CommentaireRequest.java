package com.msr.cg.afrimeta.commentaire.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public record CommentaireRequest(

        @RequestParam String commentaireDate,

        @NotNull
        @RequestParam("description") String description,

        @NotNull
        @PathVariable("commentaireId") String commentaireId,

        @NotNull
        @RequestParam("clientUserId") String clientUserId,

        @NotNull
        @PathVariable("produitId") String produitId

) {
}
