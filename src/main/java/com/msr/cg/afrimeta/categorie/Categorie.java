package com.msr.cg.afrimeta.categorie;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;



@Entity
@Table(name = "categorie")
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "categorie_id")
    private Long categorieId;

    @Length(min = 1, max = 50)
    private String nom;

    public Categorie() {
    }

    public Categorie(String nom) {
        this.nom = nom;
    }

    public Categorie(Long categorieId, String nom) {
        this(nom);
        this.categorieId = categorieId;
    }

    public Long getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(Long categorieId) {
        this.categorieId = categorieId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
