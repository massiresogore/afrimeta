package com.msr.cg.afrimeta.magasin;

import com.msr.cg.afrimeta.clientUser.ClientUser;
import jakarta.persistence.*;

@Entity
@Table(name = "magasin")
public class Magasin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long magasinId;
    String libele;
    @Column(name = "logo_url")
    String logoUrl;
    String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private ClientUser clientUser;

    public Magasin() {
    }

    public Magasin(String libele, String logoUrl, String description, ClientUser clientUser) {
        this.libele = libele;
        this.logoUrl = logoUrl;
        this.description = description;
        this.clientUser = clientUser;
    }

    public Magasin(Long magasinId, String libele, String logoUrl, String description, ClientUser clientUser) {
        this(libele, logoUrl, description, clientUser);
        this.magasinId = magasinId;
    }

    public Long getMagasinId() {
        return magasinId;
    }

    public void setMagasinId(Long magasinId) {
        this.magasinId = magasinId;
    }

    public String getLibele() {
        return libele;
    }

    public void setLibele(String libele) {
        this.libele = libele;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ClientUser getClientUser() {
        return clientUser;
    }

    public void setClientUser(ClientUser clientUser) {
        this.clientUser = clientUser;
    }

    @Override
    public String toString() {
        return "Magasin{" +
                "magasinId=" + magasinId +
                ", libele='" + libele + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", description='" + description + '\'' +
                ", clientUser=" + clientUser +
                '}';
    }
}
