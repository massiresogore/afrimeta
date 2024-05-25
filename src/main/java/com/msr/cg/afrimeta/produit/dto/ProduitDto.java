package com.msr.cg.afrimeta.produit.dto;

import com.msr.cg.afrimeta.categorie.Categorie;
import com.msr.cg.afrimeta.typeproduit.TypeProduit;
import com.msr.cg.afrimeta.website.Website;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Max;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

public record ProduitDto(
    Long produitId,

    @Length(max = 100, min = 5)
    String titre,

    @Length(max = 500, min = 5)
    String description,

    @Max(100000)
    int quantiteStock,

    @Length(max = 100, min = 5)
    String imageUrl,

    @DecimalMax("1000000")
    double prix,

    LocalDate dateAjout,

    Categorie categorie,

    TypeProduit typeProduit,

    Website website
) {
}
