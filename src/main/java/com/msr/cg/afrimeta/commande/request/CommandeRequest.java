package com.msr.cg.afrimeta.commande.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;

public record CommandeRequest(

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
    @PathVariable("clientUserId") String clientUserId
){}
