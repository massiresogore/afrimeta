package com.msr.cg.afrimeta.ville;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;

@Entity
public class Ville {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ville_id")
    private Long villeId;
    @Length(min = 3, max = 50)
    private String nom;

    public Ville() {
    }

    public Ville(String nom) {
        this.nom = nom;
    }
    public Ville(Long villeId, String nom) {
        this(nom);
        this.villeId = villeId;
    }

    public Long getVilleId() {
        return villeId;
    }

    public void setVilleId(Long villeId) {
        this.villeId = villeId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Ville{" +
                "villeId=" + villeId +
                ", nom='" + nom + '\'' +
                '}';
    }
}
