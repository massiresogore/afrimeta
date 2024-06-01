package com.msr.cg.afrimeta.clientUser.dto;

import com.msr.cg.afrimeta.profile.Profile;
import jakarta.validation.constraints.NotNull;

public record ClientUserRequest(
        Long user_id,
        @NotNull
        String username,
        @NotNull
        String email,
        @NotNull
        String password,
        Profile profile
) {
}
