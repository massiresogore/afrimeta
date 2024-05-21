package com.msr.cg.afrimeta.clientUser;

import com.msr.cg.afrimeta.profile.Profile;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import org.hibernate.validator.constraints.Length;

@Entity
public class ClientUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    @Column(nullable = false, unique = true)
    @Length(min = 3, max = 50)
    private String username;

    @Column(nullable = false, unique = true)
    @Length(min = 5, max = 50)
    private String email;

    @Column(nullable = false)
    @Length(min = 8, max = 200)
    private String password;

    @Column(nullable = false)
    private boolean enable;
    @Column(nullable = false)
    private String role;

    @OneToOne(cascade = CascadeType.ALL )
    @JoinColumn(name = "profile_id")
    private Profile profile;

    public ClientUser() {
    }

    //Pour la création
    public ClientUser(
        String username,
        String email,
        String password,
        boolean enable,
        String role,
        Profile profile
    ) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.enable = enable;
        this.role = role;
    }

    //Pour la transformation
    public ClientUser(
                    Long user_id,
                        String username,
                      String email,
                      String password,
                      boolean enable,
                      String role,
                    Profile profile
            ){
        this(username,email,password,enable,role,profile);
        this.user_id = user_id;
    }


    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "ClientUser{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", enable=" + enable +
                ", role='" + role + '\'' +
                '}';
    }
}
