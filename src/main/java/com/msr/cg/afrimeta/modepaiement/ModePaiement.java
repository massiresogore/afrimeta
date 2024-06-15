package com.msr.cg.afrimeta.modepaiement;


import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;



@Entity
@Table(name = "mode_paiement")
public class ModePaiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "mode_paiement_id")
    private Long modePaiementId;

    @Length(min = 1, max = 50)
    private String nom;

    public ModePaiement() {
    }

    public ModePaiement(String nom) {
        this.nom = nom;
    }

    public ModePaiement(Long modePaiementId, String nom) {
        this(nom);
        this.modePaiementId = modePaiementId;
    }

    public Long getModePaiementId() {
        return modePaiementId;
    }

    public void setModePaiementId(Long modePaiementId) {
        this.modePaiementId = modePaiementId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "ModePaiement{" +
                "modePaiementId=" + modePaiementId +
                ", nom='" + nom + '\'' +
                '}';
    }
}
