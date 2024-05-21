package com.msr.cg.afrimeta.website;


import com.msr.cg.afrimeta.magasin.Magasin;
import jakarta.persistence.*;

@Entity
@Table(name = "website")
public class Website {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "website_id")
    private Long websiteId;
    @Column(name = "website_url")
    private String websiteUrl;

    @ManyToOne(cascade = {CascadeType.PERSIST,
                            CascadeType.MERGE,
                            CascadeType.REFRESH,
                            CascadeType.DETACH
                            },
            fetch = FetchType.LAZY
    )
    private Magasin magasin;

    public Website() {
    }

    public Website(String websiteUrl, Magasin magasin) {
        this.websiteUrl = websiteUrl;
        this.magasin = magasin;
    }

    public Website(Long websiteId, String websiteUrl, Magasin magasin) {
        this(websiteUrl, magasin);
        this.websiteId = websiteId;
    }

    public Long getWebsiteId() {
        return websiteId;
    }

    public void setWebsiteId(Long websiteId) {
        this.websiteId = websiteId;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public Magasin getMagasin() {
        return magasin;
    }

    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }

    @Override
    public String toString() {
        return "Website{" +
                "websiteUrl='" + websiteUrl + '\'' +
                ", websiteId=" + websiteId +
                '}';
    }
}
