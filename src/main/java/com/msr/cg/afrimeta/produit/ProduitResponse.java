package com.msr.cg.afrimeta.produit;

import com.msr.cg.afrimeta.categorie.Categorie;
import com.msr.cg.afrimeta.couleur.Couleur;
import com.msr.cg.afrimeta.image.Image;
import com.msr.cg.afrimeta.typeproduit.TypeProduit;
import com.msr.cg.afrimeta.website.Website;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


public class ProduitResponse {
    Long produitId;

    @Length(max = 100, min = 5)
    String titre;

    @Length(max = 500, min = 5)
    String description;

    @Min(1)
    @Max(100000)
    int quantiteStock;

    @DecimalMin("0.5")
    @DecimalMax("1000000")
    double prix;

    @CreationTimestamp
    LocalDate dateAjout;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name = "categorie_id")
    Categorie categorie;

    TypeProduit typeProduit;

    Website website;

    Set<Couleur> couleurs;


    private Set<Image> images = new HashSet<>();

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

    public Set<Couleur> getCouleurs() {
        return couleurs;
    }

    public void setCouleurs(Set<Couleur> couleurs) {
        this.couleurs = couleurs;
    }

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }
}
