package com.msr.cg.afrimeta.taille;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "taille")
public class Taille {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "taille_id")
    private Long tailleId;

    @Length(min = 1, max = 50)
    private String nom;

    public Taille() {
    }

    public Taille(String nom) {
        this.nom = nom;
    }

    public Taille(Long tailleId, String nom) {
        this(nom);
        this.tailleId = tailleId;
    }

    public Long getTailleId() {
        return tailleId;
    }

    public void setTailleId(Long tailleId) {
        this.tailleId = tailleId;
    }

    public @Length(min = 3, max = 50) String getNom() {
        return nom;
    }

    public void setNom(@Length(min = 3, max = 50) String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "taille{" +
                "tailleId=" + tailleId +
                ", nom='" + nom + '\'' +
                '}';
    }
}
