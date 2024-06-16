package com.msr.cg.afrimeta.livraison.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public record LivraisonRequest(

        @RequestParam String livraisonDate,

        @NotNull
        @RequestParam("description") String description,

        @NotNull
        @PathVariable("livraisonId") String livraisonId,

        @NotNull
        @RequestParam("modelivraisonId") String modelivraisonId,

        @NotNull
        @PathVariable("commandeId") String commandeId

) {
}
