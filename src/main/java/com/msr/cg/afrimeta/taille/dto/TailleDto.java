package com.msr.cg.afrimeta.taille.dto;

import org.hibernate.validator.constraints.Length;

public record TailleDto(
       Long tailleId,
       @Length(min=1, max=150)
       String      nom
) {
}
