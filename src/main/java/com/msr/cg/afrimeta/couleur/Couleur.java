package com.msr.cg.afrimeta.couleur;

import com.msr.cg.afrimeta.produit.Produit;
import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "couleur")
public class Couleur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "couleur_id")
    private Long couleurId;

    @Length(min = 1, max = 50)
    @Column(unique = true, nullable = false)
    private String nom;

    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
            @JoinTable(
                    name = "couleur_produit",
                    joinColumns = @JoinColumn(name = "couleur_id"),
                    inverseJoinColumns = @JoinColumn(name = "produit_id")
            )
    private List<Produit> produits;

    public Couleur() {
    }

    public Couleur(String nom) {
        this.nom = nom;
    }

    public Couleur(Long couleurId, String nom) {
        this(nom);
        this.couleurId = couleurId;
    }

    public Long getCouleurId() {
        return couleurId;
    }

    public void setCouleurId(Long couleurId) {
        this.couleurId = couleurId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public  void addProduit(Produit produit) {
        if (produits == null) {
            produits = new ArrayList<>();
        }
        produits.add(produit);
    }


    @Override
    public String toString() {
        return "Couleur{" +
                "couleurId=" + couleurId +
                ", nom='" + nom + '\'' +
                '}';
    }
}
