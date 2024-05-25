package com.msr.cg.afrimeta.couleur.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

public record CouleurDto(
       Long couleurId,
       @Length(min=1, max=150)
       @UniqueElements
       String      nom
) {
}
