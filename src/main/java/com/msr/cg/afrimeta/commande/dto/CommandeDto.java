package com.msr.cg.afrimeta.commande.dto;

import com.msr.cg.afrimeta.clientUser.ClientUser;
import com.msr.cg.afrimeta.facture.Facture;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

public record CommandeDto (

    Long commandeId,

    @CreationTimestamp
    LocalDate commandeDate,

    @NotNull
    @Column(name = "commande_total")
    int commandeTotal,

    @NotNull
    String adresse,

    @NotNull
    double prixTotal,

    @NotNull
    int nombreProduit,

    @NotNull
    ClientUser clientUser,

    @NotNull
    Facture facture
){}
