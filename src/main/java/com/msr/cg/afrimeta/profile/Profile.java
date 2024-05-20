package com.msr.cg.afrimeta.profile;

import com.msr.cg.afrimeta.clientUser.ClientUser;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long profileId;


    private String nom;
    private String prenom;
    @Column(name = "numero_telephone")
    private String numeroTelephone;
    @Column(name = "date_naissance")
    private LocalDate dateNaissance;
    @Enumerated(EnumType.STRING)
    private GenreEnum genre;
    private String addresse;
    private String ville;
    @Column(name = "code_postal")
    private String codePostal;
    private String pays;

    @Column(name = "profile_picture_url")
    private String profilePictureUrl;

    private String bio;

    @OneToOne(mappedBy = "profile",
            fetch = FetchType.LAZY,cascade = {
                                                CascadeType.DETACH,
                                                CascadeType.MERGE,
                                                CascadeType.REFRESH,
                                                CascadeType.PERSIST
                                            }
            )
    private ClientUser clientUser;

    public Profile() {
    }

    public Profile(
           String nom,
           String prenom,
           String numeroTelephone,
           LocalDate dateNaissance,
           GenreEnum genre,
           String addresse,
           String ville,
           String codePostal,
           String pays,
           String profilePictureUrl,
           String bio
    ) {
        this.nom = nom;
        this.prenom = prenom;
        this.numeroTelephone = numeroTelephone;
        this.dateNaissance = dateNaissance;
        this.genre = genre;
        this.addresse = addresse;
        this.ville = ville;
        this.codePostal = codePostal;
        this.pays = pays;
        this.profilePictureUrl = profilePictureUrl;
        this.bio = bio;
    }

    public Profile(
           Long profileId,
           String nom,
           String prenom,
           String numeroTelephone,
           LocalDate dateNaissance,
           GenreEnum genre,
           String addresse,
           String ville,
           String codePostal,
           String pays,
           String profilePictureUrl,
           String bio
    ) {
        this(nom,prenom,numeroTelephone,dateNaissance,genre,addresse,ville,codePostal,pays,profilePictureUrl,bio);
        this.profileId = profileId;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public GenreEnum getGenre() {
        return genre;
    }

    public void setGenre(GenreEnum genre) {
        this.genre = genre;
    }

    public String getAddresse() {
        return addresse;
    }

    public void setAddresse(String addresse) {
        this.addresse = addresse;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String ProfilePictureUrl) {
        this.profilePictureUrl = ProfilePictureUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public ClientUser getClientUser() {
        return clientUser;
    }

    public void setClientUser(ClientUser clientUser) {
        this.clientUser = clientUser;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "profileId=" + profileId +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", numeroTelephone='" + numeroTelephone + '\'' +
                ", dateNaissance=" + dateNaissance +
                ", genre=" + genre +
                ", addresse='" + addresse + '\'' +
                ", ville='" + ville + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", pays='" + pays + '\'' +
                ", profilePictureUrl='" + profilePictureUrl + '\'' +
                ", bio='" + bio + '\'' +
              //  ", clientUser=" + clientUser +
                '}';
    }
}
