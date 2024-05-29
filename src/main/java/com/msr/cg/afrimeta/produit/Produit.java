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
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "produit")
@NamedEntityGraph(name = "Produit.categorie", attributeNodes = {@NamedAttributeNode("categorie"),@NamedAttributeNode("typeProduit"),@NamedAttributeNode("website"),@NamedAttributeNode("images")})
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "produit_id")
    Long produitId;

    @Column(name = "titre", nullable = false)
    @Length(max = 100, min = 5)
    String titre;

    @Length(max = 500, min = 5)
    String description;

    @Column(name = "quantite_stock", nullable = false)
    @Min(1)
    @Max(100000)
    int quantiteStock;

    @DecimalMin("0.5")
    @DecimalMax("1000000")
    double prix;

    @Column(name = "date_ajout", nullable = false)
    @CreationTimestamp
    LocalDate dateAjout;

    @Column(name = "image_url")
    String image_url;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name = "categorie_id")
//            @JsonIgnore
    Categorie categorie;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name = "type_produit_id")
//            @JsonIgnore
    TypeProduit typeProduit;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name = "website_id", nullable = false)
           // @JsonIgnore
    Website website;

    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH})
            @JoinTable(
                    name = "couleur_produit",
                    joinColumns = @JoinColumn(name = "produit_id"),
                    inverseJoinColumns = @JoinColumn(name = "couleur_id")
            )
//            @JsonIgnore
    List<Couleur> couleurs;

    @OneToMany(mappedBy = "produit",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JsonIgnore
    private List<Image> images;

  /*  @ElementCollection
    @CollectionTable(name = "image", joinColumns = @JoinColumn(name = "produit_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "type", column = @Column(name = "type")),
            @AttributeOverride(name = "filePath", column = @Column(name = "file_path")),
            @AttributeOverride(name = "name", column = @Column(name = "name"))
    })

    private List<Image> images = new HashSet<>();*/


    public Produit() {}

    public Produit(Long produitId, String titre, String description, int quantite_stock, double prix, LocalDate dateAjout, Categorie categorie, TypeProduit typeProduit, Website website) {
        this.produitId = produitId;
        this.titre = titre;
        this.description = description;
        this.quantiteStock = quantite_stock;
        this.prix = prix;
        this.dateAjout = dateAjout;
        this.categorie = categorie;
        this.typeProduit = typeProduit;
        this.website = website;
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

    public void setQuantiteStock(@Min(1) @Max(100000) int quantite_stock) {
        this.quantiteStock = quantite_stock;
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

    public List<Couleur> getCouleurs() {
        return couleurs;
    }

    public void setCouleurs(List<Couleur> couleurs) {
        this.couleurs = couleurs;
    }

    public void addCouleur(Couleur couleur) {
        if (couleurs == null) {
            couleurs = new ArrayList<>();
        }
        couleurs.add(couleur);
    }

    public void addImage(Image image) {
        if (this.images == null) {
            this.images =new  ArrayList<>();
        }
        this.images.add(image);
        image.setProduct(this);
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    //    public List<Image> getImages() {
//        return images;
//    }
//
//    public void setImages(List<Image> images) {
//        this.images = images;
//    }






    @Override
    public String toString() {
        return "Produit{" +
                "produitId=" + produitId +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", quantiteStock=" + quantiteStock +
                ", prix=" + prix +
                ", dateAjout=" + dateAjout +
                '}';
    }
}
