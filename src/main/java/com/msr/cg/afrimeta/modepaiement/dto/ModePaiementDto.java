package com.msr.cg.afrimeta.modepaiement.dto;

import org.hibernate.validator.constraints.Length;

public record ModePaiementDto(
       Long modePaiementId,
       @Length(min=1, max=150)
       String      nom
) {
}
