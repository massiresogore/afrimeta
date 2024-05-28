package com.msr.cg.afrimeta.website.dto;

import jakarta.validation.constraints.NotNull;

public record SingleWebsiteResponse(
         Long websiteId,
         @NotNull
         String websiteUrl) {
}
