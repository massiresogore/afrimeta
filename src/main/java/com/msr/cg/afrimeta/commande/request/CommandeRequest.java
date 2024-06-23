package com.msr.cg.afrimeta.commande.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

public record CommandeRequest(

    Long commandeId,

    @CreationTimestamp
    LocalDateTime commandeDate,

    @NotNull
    @Column(name = "commande_total")
    String commandeTotal,

    @NotNull
    String adresse,

    @NotNull
    String prixTotal,

    @NotNull
    String nombreProduit,

    List<Panier> paniers
        //,

   // @PathVariable("clientUserId") String clientUserId
){}
