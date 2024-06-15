package com.msr.cg.afrimeta.modelivraison;


import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;



@Entity
@Table(name = "mode_livraison")
public class ModeLivraison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "modeLivraison_id")
    private Long modeLivraisonId;

    @Length(min = 1, max = 50)
    private String nom;

    public ModeLivraison() {
    }

    public ModeLivraison(String nom) {
        this.nom = nom;
    }

    public ModeLivraison(Long modeLivraisonId, String nom) {
        this(nom);
        this.modeLivraisonId = modeLivraisonId;
    }

    public Long getModeLivraisonId() {
        return modeLivraisonId;
    }

    public void setModeLivraisonId(Long modeLivraisonId) {
        this.modeLivraisonId = modeLivraisonId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "ModeLivraison{" +
                "modeLivraisonId=" + modeLivraisonId +
                ", nom='" + nom + '\'' +
                '}';
    }
}
