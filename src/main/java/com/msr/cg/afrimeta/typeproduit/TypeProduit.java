package com.msr.cg.afrimeta.typeproduit;

import com.msr.cg.afrimeta.taille.Taille;
import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@Table(name = "type_produit")
public class TypeProduit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "type_produit_id")
    private Long typeProduitId;

    @Length(min = 3, max = 50)
    private String nom;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(
            name = "lier",
            joinColumns = @JoinColumn(name = "type_produit_id"),
            inverseJoinColumns = @JoinColumn(name = "taille_id")
    )
    private List<Taille> tailles;

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
