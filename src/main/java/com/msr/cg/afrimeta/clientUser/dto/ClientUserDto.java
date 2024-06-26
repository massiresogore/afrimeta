package com.msr.cg.afrimeta.clientUser.dto;

import com.msr.cg.afrimeta.profile.Profile;

public record ClientUserDto(
        Long user_id,
        String username,
        String email,
      //  String password,
        boolean enable,
        String role,
        Profile profile
) {
}
