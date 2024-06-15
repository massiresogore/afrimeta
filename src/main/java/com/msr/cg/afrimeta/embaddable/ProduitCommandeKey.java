package com.msr.cg.afrimeta.embaddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ProduitCommandeKey {
    @Column(name = "produit_id")
    private Long produitId;
    @Column(name = "commande_id")
    private Long commandeId;

    public ProduitCommandeKey() {
    }

    public ProduitCommandeKey(Long produitId, Long commandeId) {
        this.produitId = produitId;
        this.commandeId = commandeId;
    }

    public Long getProduitId() {
        return produitId;
    }

    public void setProduitId(Long produitId) {
        this.produitId = produitId;
    }

    public Long getCommandeId() {
        return commandeId;
    }

    public void setCommandeId(Long commandeId) {
        this.commandeId = commandeId;
    }
}
