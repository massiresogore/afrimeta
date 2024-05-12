package com.msr.cg.afrimeta.clientUser.dto;

import jakarta.validation.constraints.NotNull;

public record ClientUserDto(
        @NotNull
        Long user_id,
        @NotNull
        String nom,
        String prenom,
        @NotNull
        String email,
        @NotNull
        String password,
        @NotNull
        String telephone,
        @NotNull
        String raisonSocial,
        boolean enable,
        String role,
        int adresse
) {
}
