package com.msr.cg.afrimeta.magasin.dto;

import com.msr.cg.afrimeta.clientUser.ClientUser;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public record MagasinRequest(
        String libele,
        String description,
        MultipartFile logoFile
) {}
