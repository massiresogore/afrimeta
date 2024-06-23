package com.msr.cg.afrimeta.clientUser.dto;

import com.msr.cg.afrimeta.profile.Profile;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record ClientUserRequest(
        Long user_id,
        @NotNull
        String username,
        @NotNull
        String email,

        @NotNull
        @Length(min = 8, max = 200)
        String password,

        Profile profile
) {
}
