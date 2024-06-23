package com.msr.cg.afrimeta.embaddable;

import com.msr.cg.afrimeta.commande.Commande;
import com.msr.cg.afrimeta.produit.Produit;
import jakarta.persistence.*;

@Entity
public class Contenir {

    @EmbeddedId
    private ProduitCommandeKey id;

    @ManyToOne(cascade = {CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.DETACH
    },fetch = FetchType.LAZY)
    @MapsId("produitId")
    @JoinColumn(name = "produit_id")
    private Produit produit;

    @ManyToOne(cascade = {CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.DETACH
    },fetch = FetchType.LAZY)
    @MapsId("commandeId")
    @JoinColumn(name = "commande_id")
    private Commande commande;

    private int quantite;

    public Contenir() {
    }

    public Contenir(ProduitCommandeKey id, Produit produit, Commande commande, int quantite) {
        this.id = id;
        this.produit = produit;
        this.commande = commande;
        this.quantite = quantite;
    }

    public ProduitCommandeKey getId() {
        return id;
    }

    public void setId(ProduitCommandeKey id) {
        this.id = id;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @Override
    public String toString() {
        return "Contenir{" +
                "id=" + id +
                ", produit=" + produit.getProduitId() +
                ", commande=" + commande.getCommandeId() +
                ", quantite=" + quantite +
                '}';
    }
}
