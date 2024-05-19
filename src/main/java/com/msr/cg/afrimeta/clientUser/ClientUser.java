package com.msr.cg.afrimeta.clientUser;

import jakarta.persistence.*;

@Entity
public class ClientUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    private String username;
    private String email;
    private String password;
    private boolean enable;
    private String role;

    public ClientUser() {
    }

    //Pour la création
    public ClientUser(
        String username,
        String email,
        String password,
        boolean enable,
        String role
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
                      String role
            ){
        this(username,email,password,enable,role);
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
