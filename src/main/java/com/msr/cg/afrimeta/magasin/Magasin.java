package com.msr.cg.afrimeta.magasin;

import com.msr.cg.afrimeta.clientUser.ClientUser;
import jakarta.persistence.*;

@Entity
@Table(name = "magasin")
public class Magasin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long magasinId;
    private String libele;
    private String description;
    @Column(name = "logo_url")
   private String logo;

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

    public Magasin() {
    }

    public Magasin(String libele, String description, ClientUser clientUser, String logo) {
        this.libele = libele;
        this.description = description;
        this.clientUser = clientUser;
        this.logo = logo;
    }

    public Magasin(Long magasinId, String libele, String description, ClientUser clientUser, String logo) {
        this(libele, description, clientUser,logo);
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Override
    public String toString() {
        return "Magasin{" +
                "magasinId=" + magasinId +
                ", libele='" + libele + '\'' +
                ", description='" + description + '\'' +
                ", logo='" + logo + '\'' +
                '}';
    }
}
