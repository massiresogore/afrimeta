package com.msr.cg.afrimeta.paiement.dto;

import com.msr.cg.afrimeta.commande.dto.CommandeDto;
import com.msr.cg.afrimeta.modepaiement.dto.ModePaiementDto;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

public record PaiementDto(
        Long paimementId,
        String paiementDate,

        @NotNull
        String description,

        ModePaiementDto modePaiement,

        CommandeDto commande
) {
}
