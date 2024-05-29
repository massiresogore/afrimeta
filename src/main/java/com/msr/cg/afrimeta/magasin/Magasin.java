package com.msr.cg.afrimeta.magasin;

import com.msr.cg.afrimeta.clientUser.ClientUser;
import jakarta.persistence.*;
import org.springframework.data.annotation.Reference;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "magasin")
public class Magasin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long magasinId;
    private String libele;
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
    @JoinColumn(name = "user_id")
    private ClientUser clientUser;

    @ElementCollection()
    @CollectionTable(name = "logo", joinColumns = @JoinColumn(name = "magasin_id"))
    @MapKeyColumn(name = "file_path")
    @Column(name = "logo_name", unique = true, nullable = false)
    private Map<String,String> logo = new HashMap<>();

    public Magasin() {
    }

    public Magasin(String libele, String description, ClientUser clientUser, Map<String, String> logo) {
        this.libele = libele;
        this.description = description;
        this.clientUser = clientUser;
        this.logo = logo;
    }

    public Magasin(Long magasinId, String libele, String description, ClientUser clientUser, Map<String, String> logo) {
       this(libele, description, clientUser, logo);
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

    public Map<String, String> getLogo() {
        return logo;
    }

    public void setLogo(Map<String, String> logo) {
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
