package com.msr.cg.afrimeta.commande;

import com.msr.cg.afrimeta.clientUser.ClientUser;
import com.msr.cg.afrimeta.embaddable.Contenir;
import com.msr.cg.afrimeta.embaddable.ProduitCommandeKey;
import com.msr.cg.afrimeta.produit.Produit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "commande")
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commandeId;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @CreationTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @NotNull
    @Column(name = "commande_total")
    private double commandeTotal;

    @NotNull
    @Column(name = "adresse")
    private String adresse;

    @NotNull
    @Column(name = "prix_total")
    private double prixTotal;

    @NotNull
    @Column(name = "nombre_produit")
    private int nombreProduit;

   @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private ClientUser clientUser;


    /*@NotNull
    @OneToOne(
            mappedBy = "commande",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "facture_id")
    private Facture facture;*/


    public Commande(LocalDateTime createdAt, double commandeTotal, String adresse, double prixTotal, int nombreProduit, ClientUser clientUser) {
        this.createdAt = createdAt;
        this.commandeTotal = commandeTotal;
        this.adresse = adresse;
        this.prixTotal = prixTotal;
        this.nombreProduit = nombreProduit;
        this.clientUser = clientUser;
    }

    public Commande(Long commandeId, LocalDateTime createdAt, double commandeTotal, String adresse, double prixTotal, int nombreProduit, ClientUser clientUser) {
        this(createdAt,commandeTotal,adresse,prixTotal,nombreProduit,clientUser);
        this.commandeId = commandeId;
    }

    public Commande( LocalDateTime createdAt, LocalDateTime updatedAt, double commandeTotal, String adresse, double prixTotal, int nombreProduit, ClientUser clientUser) {
        this(createdAt,commandeTotal,adresse,prixTotal,nombreProduit,clientUser);
        this.updatedAt = updatedAt;
    }

    public Commande() {

    }

    public Commande(Long commandeId, LocalDateTime createdAt, LocalDateTime updatedAt, double commandeTotal, String adresse, double prixTotal, int nombreProduit, ClientUser clientUser) {
        this.commandeId = commandeId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.commandeTotal = commandeTotal;
        this.adresse = adresse;
        this.prixTotal = prixTotal;
        this.nombreProduit = nombreProduit;
        this.clientUser = clientUser;
    }

    public Long getCommandeId() {
        return commandeId;
    }

    public void setCommandeId(Long commandeId) {
        this.commandeId = commandeId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime commandeDate) {
        this.createdAt = commandeDate;
    }

    @NotNull
    public double getCommandeTotal() {
        return commandeTotal;
    }

    public void setCommandeTotal(double commandeTotal) {
        this.commandeTotal = commandeTotal;
    }

    public @NotNull String getAdresse() {
        return adresse;
    }

    public void setAdresse(@NotNull String adresse) {
        this.adresse = adresse;
    }

    @NotNull
    public double getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(@NotNull double prixTotal) {
        this.prixTotal = prixTotal;
    }

    @NotNull
    public int getNombreProduit() {
        return nombreProduit;
    }

    public void setNombreProduit(@NotNull int nombreProduit) {
        this.nombreProduit = nombreProduit;
    }

    public @NotNull ClientUser getClientUser() {
        return clientUser;
    }

    public void setClientUser(@NotNull ClientUser clientUser) {
        this.clientUser = clientUser;
    }

   /* public  Facture getFacture() {
        return facture;
    }

    public void setFacture( Facture facture) {
        this.facture = facture;
    }*/


    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }



    @Override
    public String toString() {
        return "Commande{" +
                "commandeId=" + commandeId +
                ", commandeDate=" + createdAt +
                ", commandeTotal=" + commandeTotal +
                ", adresse='" + adresse + '\'' +
                ", prixTotal=" + prixTotal +
                ", nombreProduit=" + nombreProduit +
                '}';
    }


}
