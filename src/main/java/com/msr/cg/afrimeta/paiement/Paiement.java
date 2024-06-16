package com.msr.cg.afrimeta.paiement;

import com.msr.cg.afrimeta.commande.Commande;
import com.msr.cg.afrimeta.modepaiement.ModePaiement;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "paiement")
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paiement_id")
    private Long paimementId;

    @CreationTimestamp
    @Column(name = "paiement_date")
    private LocalDateTime paiementDate;

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
    @JoinColumn(name = "mode_paiement_id")
    private ModePaiement modePaiement;

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

    public Paiement() {
    }

    public Paiement(LocalDateTime paiementDate, String description, ModePaiement modePaiement, Commande commande) {
        this.paiementDate = paiementDate;
        this.description = description;
        this.modePaiement = modePaiement;
        this.commande = commande;
    }

    public Paiement(Long paimementId, LocalDateTime paiementDate, String description, ModePaiement modePaiement, Commande commande) {
        this(paiementDate, description, modePaiement, commande);
        this.paimementId = paimementId;
    }

    public Long getPaimementId() {
        return paimementId;
    }

    public void setPaimement(Long paimementId) {
        this.paimementId = paimementId;
    }

    public LocalDateTime getPaiementDate() {
        return paiementDate;
    }

    public void setPaiementDate(LocalDateTime paiementDate) {
        this.paiementDate = paiementDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ModePaiement getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(ModePaiement modePaiement) {
        this.modePaiement = modePaiement;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    @Override
    public String toString() {
        return "Paiement{" +
                "paimementId=" + paimementId +
                ", paiementDate=" + paiementDate +
                ", description='" + description + '\'' +
                '}';
    }
}
