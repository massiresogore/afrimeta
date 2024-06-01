package com.msr.cg.afrimeta.produit.dto.dto;

import com.msr.cg.afrimeta.categorie.dto.CategorieResponse;
import com.msr.cg.afrimeta.couleur.Couleur;
import com.msr.cg.afrimeta.couleur.dto.CouleurResponse;
import com.msr.cg.afrimeta.image.ImageDto;
import com.msr.cg.afrimeta.typeproduit.TypeProduit;
import com.msr.cg.afrimeta.typeproduit.dto.TypeProduitResponse;
import com.msr.cg.afrimeta.website.Website;
import com.msr.cg.afrimeta.website.dto.SingleWebsiteResponse;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.List;

public record ProduitResponse(
    Long produitId,

    @NotNull
    @Length(max = 100, min = 5)
    String titre,

    @Length(max = 500, min = 5)
    String description,

    @Max(100000)
    int quantiteStock,

    @DecimalMax("1000000")
    double prix,

    LocalDate dateAjout,

    @NotNull
    CategorieResponse categorie,

    @NotNull
    TypeProduitResponse typeProduit,

    @NotNull
    SingleWebsiteResponse website,

    List<CouleurResponse> couleurs,

    List<List<byte[]>> images,

    List<ImageDto> imagePath
) {
}
