package com.msr.cg.afrimeta.taille;

import com.msr.cg.afrimeta.typeproduit.TypeProduit;
import jakarta.persistence.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@Table(name = "taille")
public class Taille {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "taille_id")
    private Long tailleId;

    @Length(min = 1, max = 50)
    private String nom;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            name="taille_type_produit",
            joinColumns = @JoinColumn(name = "taille_id"),
            inverseJoinColumns = @JoinColumn(name = "type_produit_id")
    )
    private List<TypeProduit> typeProduits;

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

    public List<TypeProduit> getTypeProduits() {
        return typeProduits;
    }

    public void setTypeProduits(List<TypeProduit> typeProduits) {
        this.typeProduits = typeProduits;
    }

    @Override
    public String toString() {
        return "taille{" +
                "tailleId=" + tailleId +
                ", nom='" + nom + '\'' +
                '}';
    }
}
