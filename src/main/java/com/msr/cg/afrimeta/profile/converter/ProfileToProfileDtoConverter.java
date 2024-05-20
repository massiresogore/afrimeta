package com.msr.cg.afrimeta.profile.converter;

import com.msr.cg.afrimeta.profile.Profile;
import com.msr.cg.afrimeta.profile.dto.ProfileDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Date;

@Component
public class ProfileToProfileDtoConverter implements Converter<Profile, ProfileDto> {
    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public ProfileDto convert(Profile source) {
        return new ProfileDto(
                source.getProfileId(),
                source.getNom(),
                source.getPrenom(),
                source.getNumeroTelephone(),
                source.getDateNaissance().toString(),
                source.getGenre(),
                source.getAddresse(),
                source.getVille(),
                source.getCodePostal(),
                source.getPays(),
                source.getProfilePictureUrl(),
                source.getBio()
        );
    }
}
