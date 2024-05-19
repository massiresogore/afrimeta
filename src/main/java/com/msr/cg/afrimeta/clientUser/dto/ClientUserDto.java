package com.msr.cg.afrimeta.clientUser.dto;

import com.msr.cg.afrimeta.profile.Profile;
import jakarta.validation.constraints.NotNull;

public record ClientUserDto(
        @NotNull
        Long user_id,
        String username,
        @NotNull
        String email,
        @NotNull
        String password,
        boolean enable,
        String role,
        Profile profile
) {
}
