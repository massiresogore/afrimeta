package com.msr.cg.afrimeta.couleur;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;



@Entity
@Table(name = "couleur")
public class Couleur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "couleur_id")
    private Long couleurId;

    @Length(min = 1, max = 50)
    private String nom;

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
}
