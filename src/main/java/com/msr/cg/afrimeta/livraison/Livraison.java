package com.msr.cg.afrimeta.livraison;

import com.msr.cg.afrimeta.commande.Commande;
import com.msr.cg.afrimeta.modelivraison.ModeLivraison;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "livraison")
public class Livraison {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "livraison_id")
    private Long paimementId;

    @CreationTimestamp
    @Column(name = "livraison_date")
    private LocalDateTime livraisonDate;

    private String description;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.PERSIST
            }
    )
    @JoinColumn(name = "mode_livraison_id")
    private ModeLivraison modeLivraison;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.PERSIST
            }
    )
    @JoinColumn(name = "commande_id")
    private Commande commande;

    public Livraison() {
    }

    public Livraison(LocalDateTime livraisonDate, String description, ModeLivraison modeLivraison, Commande commande) {
        this.livraisonDate = livraisonDate;
        this.description = description;
        this.modeLivraison = modeLivraison;
        this.commande = commande;
    }

    public Livraison(Long paimementId, LocalDateTime livraisonDate, String description, ModeLivraison modeLivraison, Commande commande) {
        this(livraisonDate, description, modeLivraison, commande);
        this.paimementId = paimementId;
    }

    public Long getPaimementId() {
        return paimementId;
    }

    public void setPaimement(Long paimementId) {
        this.paimementId = paimementId;
    }

    public LocalDateTime getLivraisonDate() {
        return livraisonDate;
    }

    public void setLivraisonDate(LocalDateTime livraisonDate) {
        this.livraisonDate = livraisonDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ModeLivraison getModeLivraison() {
        return modeLivraison;
    }

    public void setModeLivraison(ModeLivraison modeLivraison) {
        this.modeLivraison = modeLivraison;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    @Override
    public String toString() {
        return "Livraison{" +
                "paimementId=" + paimementId +
                ", livraisonDate=" + livraisonDate +
                ", description='" + description + '\'' +
                '}';
    }
}
