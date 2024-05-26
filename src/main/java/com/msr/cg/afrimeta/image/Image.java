package com.msr.cg.afrimeta.image;


import com.msr.cg.afrimeta.produit.Produit;
import jakarta.persistence.*;

@Entity
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long imageId;

    private String type;

    @Column(name = "file_path")
    private String filePath;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit;

    public Image() {
    }

    public Image(String type, String filePath, String name, Produit produit) {
        this.type = type;
        this.filePath = filePath;
        this.name = name;
        this.produit = produit;
    }

    public Image(Long imageId, String type, String filePath, String name, Produit produit) {
        this(type, filePath, name, produit);
        this.imageId = imageId;
    }

    public void addProduct(Produit produit) {
        produit.addImage(this);
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Produit getProduct() {
        return produit;
    }

    public void setProduct(Produit produit) {
        this.produit = produit;
    }

    @Override
    public String toString() {
        return "Image{" +
                "imageId=" + imageId +
                ", type='" + type + '\'' +
                ", filePath='" + filePath + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
