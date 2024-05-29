package com.msr.cg.afrimeta.magasin.dto;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public record MagasinRequest(
        @RequestParam("libele") String libele,
        @RequestParam("libele") String description,
        @RequestParam("logoFile")  MultipartFile logoFile,
        @PathVariable("clientId") String clientId
) {}
