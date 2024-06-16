package com.msr.cg.afrimeta.commentaire;

import com.msr.cg.afrimeta.clientUser.ClientUser;
import com.msr.cg.afrimeta.produit.Produit;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "commentaire")
public class Commentaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentaire_id")
    private Long paimementId;

    @CreationTimestamp
    @Column(name = "commentaire_date")
    private LocalDateTime commentaireDate;

    private String contenu;

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

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.PERSIST
            }
    )
    @JoinColumn(name = "produit_id")
    private Produit produit;

    public Commentaire() {
    }

    public Commentaire(LocalDateTime commentaireDate, String contenu, ClientUser clientUser, Produit produit) {
        this.commentaireDate = commentaireDate;
        this.contenu = contenu;
        this.clientUser = clientUser;
        this.produit = produit;
    }

    public Commentaire(Long paimementId, LocalDateTime commentaireDate, String contenu, ClientUser clientUser, Produit produit) {
        this(commentaireDate, contenu, clientUser, produit);
        this.paimementId = paimementId;
    }

    public Long getPaimementId() {
        return paimementId;
    }

    public void setPaimement(Long paimementId) {
        this.paimementId = paimementId;
    }

    public LocalDateTime getCommentaireDate() {
        return commentaireDate;
    }

    public void setCommentaireDate(LocalDateTime commentaireDate) {
        this.commentaireDate = commentaireDate;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public ClientUser getClientUser() {
        return clientUser;
    }

    public void setClientUser(ClientUser clientUser) {
        this.clientUser = clientUser;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    @Override
    public String toString() {
        return "Commentaire{" +
                "paimementId=" + paimementId +
                ", commentaireDate=" + commentaireDate +
                ", contenu='" + contenu + '\'' +
                '}';
    }
}
