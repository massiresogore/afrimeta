package com.msr.cg.afrimeta.produit.dto.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


public record ProduitRequest(
        @RequestParam("image")  MultipartFile image,

        @NotNull
        @Length(max = 100, min = 5)
        @RequestParam("titre") String titre,

        @Length(max = 500, min = 5)
        @RequestParam("description") String description,

        @Max(100000)
        @RequestParam("quantiteStock")  int quantiteStock,

        @DecimalMax("1000000")
        @DecimalMin("0.5")
        @RequestParam("prix")  double prix,
        @PathVariable("websiteId") String websiteId

) {}
