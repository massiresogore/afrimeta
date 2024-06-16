package com.msr.cg.afrimeta.paiement.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

public record PaiementRequest(

        @RequestParam String paiementDate,

        @NotNull
        @RequestParam("description") String description,

        @NotNull
        @PathVariable("paiementId") String paiementId,

        @NotNull
        @RequestParam("modepaiementId") String modepaiementId,

        @NotNull
        @PathVariable("commandeId") String commandeId

) {
}
