package com.msr.cg.afrimeta.adresse;

import com.msr.cg.afrimeta.clientUser.ClientUser;
import com.msr.cg.afrimeta.ville.Ville;
import jakarta.persistence.*;

@Entity
public class Adresse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adresseId;
    private String numero;
    private int cp;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.DETACH
            }
    )
    @JoinColumn(name = "user_id")
    private ClientUser clientUser;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE,
                CascadeType.DETACH,
                CascadeType.REFRESH,
            }
    )
    @JoinColumn(name = "ville_id")
    private Ville ville;// A changé

    public Adresse() {
    }

    public Adresse(String numero, int cp, Ville ville, ClientUser clientUser) {
        this.numero = numero;
        this.cp = cp;
        this.ville = ville;
        this.clientUser = clientUser;
    }

    public Adresse (Long adresseId,String numero, int cp, Ville ville, ClientUser clientUser){
        this(numero, cp, ville, clientUser);
        this.adresseId = adresseId;
    }

    public Long getAdresseId() {
        return adresseId;
    }

    public void setAdresseId(Long adresseId) {
        this.adresseId = adresseId;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public Ville getVille() {
        return ville;
    }

    public void setVille(Ville ville) {
        this.ville = ville;
    }

    public ClientUser getClientUser() {
        return clientUser;
    }

    public void setClientUser(ClientUser clientUser) {
        this.clientUser = clientUser;
    }
}

