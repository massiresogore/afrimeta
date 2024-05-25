package com.msr.cg.afrimeta.produit.dto;

import com.msr.cg.afrimeta.categorie.Categorie;
import com.msr.cg.afrimeta.typeproduit.TypeProduit;
import com.msr.cg.afrimeta.website.Website;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

public class ProduitDtos {

    Long produitId;

    @Length(max = 100, min = 5)
    String titre;

    @Length(max = 500, min = 5)
    String description;

    @Min(1)
    @Max(100000)
    int quantiteStock;

    @Length(max = 100, min = 5)
    String imageUrl;


    @DecimalMin("0.5")
    @DecimalMax("1000000")
    double prix;

    @CreationTimestamp
    LocalDate dateAjout;

    Categorie categorie;

    TypeProduit typeProduit;

    Website website;

    public ProduitDtos() {
    }

    public ProduitDtos(Long produitId, String titre, String description, int quantiteStock, String imageUrl, double prix, LocalDate dateAjout, Categorie categorie, TypeProduit typeProduit) {
        this.produitId = produitId;
        this.titre = titre;
        this.description = description;
        this.quantiteStock = quantiteStock;
        this.imageUrl = imageUrl;
        this.prix = prix;
        this.dateAjout = dateAjout;
        this.categorie = categorie;
        this.typeProduit = typeProduit;
    }

    @Override
    public String toString() {
        return "ProduitDtos{" +
                "produitId=" + produitId +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", quantiteStock=" + quantiteStock +
                ", imageUrl='" + imageUrl + '\'' +
                ", prix=" + prix +
                ", dateAjout=" + dateAjout +
                ", categorie=" + categorie +
                ", typeProduit=" + typeProduit +
                ", website=" + website +
                '}';
    }

    public Long getProduitId() {
        return produitId;
    }

    public void setProduitId(Long produitId) {
        this.produitId = produitId;
    }

    public @Length(max = 100, min = 5) String getTitre() {
        return titre;
    }

    public void setTitre(@Length(max = 100, min = 5) String titre) {
        this.titre = titre;
    }

    public @Length(max = 500, min = 5) String getDescription() {
        return description;
    }

    public void setDescription(@Length(max = 500, min = 5) String description) {
        this.description = description;
    }

    @Min(1)
    @Max(100000)
    public int getQuantiteStock() {
        return quantiteStock;
    }

    public void setQuantiteStock(@Min(1) @Max(100000) int quantiteStock) {
        this.quantiteStock = quantiteStock;
    }

    public @Length(max = 100, min = 5) String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(@Length(max = 100, min = 5) String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @DecimalMin("0.5")
    @DecimalMax("1000000")
    public double getPrix() {
        return prix;
    }

    public void setPrix(@DecimalMin("0.5") @DecimalMax("1000000") double prix) {
        this.prix = prix;
    }

    public LocalDate getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(LocalDate dateAjout) {
        this.dateAjout = dateAjout;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public TypeProduit getTypeProduit() {
        return typeProduit;
    }

    public void setTypeProduit(TypeProduit typeProduit) {
        this.typeProduit = typeProduit;
    }

    public Website getWebsite() {
        return website;
    }

    public void setWebsite(Website website) {
        this.website = website;
    }
}
