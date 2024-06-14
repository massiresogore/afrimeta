package com.msr.cg.afrimeta.commande.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record CommandeRequest(

    Long commandeId,

    @CreationTimestamp
    LocalDateTime commandeDate,

    @NotNull
    @Column(name = "commande_total")
    int commandeTotal,

    @NotNull
    String adresse,

    @NotNull
    double prixTotal,

    @NotNull
    int nombreProduit,

    List<String> produitIds
        //,

   // @PathVariable("clientUserId") String clientUserId
){}
