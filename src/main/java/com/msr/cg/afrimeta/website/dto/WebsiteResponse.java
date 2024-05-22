package com.msr.cg.afrimeta.website.dto;

import com.msr.cg.afrimeta.magasin.Magasin;
import jakarta.validation.constraints.NotNull;

public record WebsiteResponse(
         Long websiteId,
         @NotNull
         String websiteUrl,
         Long magasin
) {
}
