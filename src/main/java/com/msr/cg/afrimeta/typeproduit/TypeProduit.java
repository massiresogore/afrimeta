package com.msr.cg.afrimeta.typeproduit;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "type_produit")
public class TypeProduit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "type_produit_id")
    private Long typeProduitId;

    @Length(min = 3, max = 50)
    private String nom;

    public TypeProduit() {
    }

    public TypeProduit(String nom) {
        this.nom = nom;
    }

    public TypeProduit(Long typeProduitId, String nom) {
        this(nom);
        this.typeProduitId = typeProduitId;
    }

    public Long getTypeProduitId() {
        return typeProduitId;
    }

    public void setTypeProduitId(Long typeProduitId) {
        this.typeProduitId = typeProduitId;
    }

    public @Length(min = 3, max = 50) String getNom() {
        return nom;
    }

    public void setNom(@Length(min = 3, max = 50) String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "typeProduit{" +
                "typeProduitId=" + typeProduitId +
                ", nom='" + nom + '\'' +
                '}';
    }
}
