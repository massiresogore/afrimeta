package com.msr.cg.afrimeta.facture.dto;


import java.time.LocalDate;

public record FactureDto (

    Long factureId,

    LocalDate factureDate,

    //@NotNull
    double totalHorsTaxe,

   // @NotNull
    double totalToutTaxeComprise,

    //@NotNull
    double totalTva

){
}
