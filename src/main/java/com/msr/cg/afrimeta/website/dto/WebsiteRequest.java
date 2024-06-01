package com.msr.cg.afrimeta.website.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.PathVariable;

public record WebsiteRequest(
         String websiteUrl,
        @PathVariable String magasinId
        ) {
}
