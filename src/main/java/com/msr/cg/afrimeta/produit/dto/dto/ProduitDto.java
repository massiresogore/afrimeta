package com.msr.cg.afrimeta.produit.dto.dto;

import com.msr.cg.afrimeta.categorie.Categorie;
import com.msr.cg.afrimeta.couleur.Couleur;
import com.msr.cg.afrimeta.typeproduit.TypeProduit;
import com.msr.cg.afrimeta.website.Website;
import com.msr.cg.afrimeta.website.dto.SingleWebsiteResponse;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.List;

public record ProduitDto(
    Long produitId,

    @NotNull
    @Length(max = 100, min = 5)
    String titre,

    @Length(max = 500, min = 5)
    String description,

//    @Max(100000)
    int quantiteStock,

//    @DecimalMax("1000000")
    double prix,

    LocalDate dateAjout,

    @NotNull
    Categorie categorie,

    @NotNull
    TypeProduit typeProduit,

    @NotNull
    Website website,

    List<Couleur> couleurs,

    List<String> images,

    SingleWebsiteResponse websiteUrl
) {
}
