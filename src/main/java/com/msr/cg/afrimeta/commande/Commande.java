package com.msr.cg.afrimeta.commande;

import com.msr.cg.afrimeta.clientUser.ClientUser;
import com.msr.cg.afrimeta.facture.Facture;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "commande")
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commandeId;

    @CreationTimestamp
    @Column(name = "commande_date")
    private LocalDate commandeDate;

    @NotNull
    @Column(name = "commande_total")
    private int commandeTotal;

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
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.PERSIST
            }
    )
    @JoinColumn(name = "user_id")
    private ClientUser clientUser;

    @NotNull
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.PERSIST
            }
    )
    @JoinColumn(name = "facture_id")
    private Facture facture;

    public Commande(){

    }

    public Commande(LocalDate commandeDate, int commandeTotal, String adresse, double prixTotal, int nombreProduit, ClientUser clientUser, Facture facture) {
        this.commandeDate = commandeDate;
        this.commandeTotal = commandeTotal;
        this.adresse = adresse;
        this.prixTotal = prixTotal;
        this.nombreProduit = nombreProduit;
        this.clientUser = clientUser;
        this.facture = facture;
    }

    public Commande(Long commandeId, LocalDate commandeDate, int commandeTotal, String adresse, double prixTotal, int nombreProduit, ClientUser clientUser, Facture facture) {
        this(commandeDate,commandeTotal,adresse,prixTotal,nombreProduit,clientUser,facture);
        this.commandeId = commandeId;
    }

    public Long getCommandeId() {
        return commandeId;
    }

    public void setCommandeId(Long commandeId) {
        this.commandeId = commandeId;
    }

    public LocalDate getCommandeDate() {
        return commandeDate;
    }

    public void setCommandeDate(LocalDate commandeDate) {
        this.commandeDate = commandeDate;
    }

    @NotNull
    public int getCommandeTotal() {
        return commandeTotal;
    }

    public void setCommandeTotal(@NotNull int commandeTotal) {
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

    public @NotNull Facture getFacture() {
        return facture;
    }

    public void setFacture(@NotNull Facture facture) {
        this.facture = facture;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "commandeId=" + commandeId +
                ", commandeDate=" + commandeDate +
                ", commandeTotal=" + commandeTotal +
                ", adresse='" + adresse + '\'' +
                ", prixTotal=" + prixTotal +
                ", nombreProduit=" + nombreProduit +
                '}';
    }
}
