package com.msr.cg.afrimeta.commande.dto;

import com.msr.cg.afrimeta.clientUser.dto.ClientUserDto;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

public record CommandeDto(

        Long commandeId,

        @CreationTimestamp
        String createdAt,

        String updatedAt,

        @NotNull
    @Column(name = "commande_total")
    double commandeTotal,

        @NotNull
    String adresse,

        @NotNull
    double prixTotal,

        @NotNull
    int nombreProduit,

        @NotNull
    ClientUserDto clientUser
){}
