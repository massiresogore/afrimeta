package com.msr.cg.afrimeta.categorie.dto;

import org.hibernate.validator.constraints.Length;

public record CategorieResponse(
       Long categorieId,
       @Length(min=1, max=150)
       String      nom
) {
}
