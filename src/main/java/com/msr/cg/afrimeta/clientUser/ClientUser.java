package com.msr.cg.afrimeta.clientUser;

import com.msr.cg.afrimeta.adresse.Adresse;
import jakarta.persistence.*;

@Entity
public class ClientUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String telephone;
    @Column(name = "raison_sociale")
    private String raisonSocial;
    private boolean enable;
    private String role;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.DETACH
            }
    )
    @JoinColumn(name = "adresse_id")
    private Adresse adresse;

    public ClientUser() {
    }

    //Pour la création
    public ClientUser(String nom,
                      String prenom,
                      String email,
                      String password,
                      String telephone,
                      Adresse adresse,
                      String raisonSocial,
                      boolean enable,
                      String role) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.telephone = telephone;
        this.adresse = adresse;
        this.raisonSocial = raisonSocial;
        this.enable = enable;
        this.role = role;
    }

    //Pour la transformation
    public ClientUser(
            Long user_id,
            String nom,
                      String prenom,
                      String email,
                      String password,
                      String telephone,
                      Adresse adresse,
                      String raisonSocial,
                      boolean enable,
                      String role){
        this(nom,prenom,email,password,telephone,adresse,raisonSocial,enable,role);
        this.user_id = user_id;
    }




    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRaisonSocial() {
        return raisonSocial;
    }

    public void setRaisonSocial(String raisonSocial) {
        this.raisonSocial = raisonSocial;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    @Override
    public String toString() {
        return "ClientUser{" +
                "user_id=" + user_id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", telephone='" + telephone + '\'' +
                ", raisonSocial='" + raisonSocial + '\'' +
                ", enable=" + enable +
                ", role='" + role + '\'' +
                ", adresse=" + adresse +
                '}';
    }
}
