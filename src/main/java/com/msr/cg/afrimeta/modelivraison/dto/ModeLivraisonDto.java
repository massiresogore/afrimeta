package com.msr.cg.afrimeta.modelivraison.dto;

import org.hibernate.validator.constraints.Length;

public record ModeLivraisonDto(
       Long modeLivraisonId,
       @Length(min=1, max=150)
       String      nom
) {
}
