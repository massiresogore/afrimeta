package com.msr.cg.afrimeta.image.old;


import jakarta.persistence.Embeddable;

@Embeddable
public class Images {
    private String type;
    private String filePath;
    private String name;

    public Images(String type, String filePath, String name) {
        this.type = type;
        this.filePath = filePath;
        this.name = name;
    }

    public Images() {
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

    @Override
    public String toString() {
        return "Image{" +
                "type='" + type + '\'' +
                ", filePath='" + filePath + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
