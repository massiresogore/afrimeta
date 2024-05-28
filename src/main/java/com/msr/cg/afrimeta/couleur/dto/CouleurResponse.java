package com.msr.cg.afrimeta.couleur.dto;

import org.hibernate.validator.constraints.Length;

public record CouleurResponse(
       Long couleurId,
       @Length(min=1, max=150)
       String      nom
) {
}
