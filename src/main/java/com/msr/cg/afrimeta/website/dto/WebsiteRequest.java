package com.msr.cg.afrimeta.website.dto;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public record WebsiteRequest(
        @RequestParam("websiteUrl") String websiteUrl,
        @PathVariable String magasinId
        ) {
}
