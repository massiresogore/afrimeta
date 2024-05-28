package com.msr.cg.afrimeta.typeproduit.dto;

import org.hibernate.validator.constraints.Length;

public record TypeProduitResponse(
       Long typeProduitId,

       @Length(min=1, max=150)
       String      nom
) {
}
