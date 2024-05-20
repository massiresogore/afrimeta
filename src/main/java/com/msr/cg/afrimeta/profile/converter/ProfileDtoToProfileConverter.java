package com.msr.cg.afrimeta.profile.converter;

import com.msr.cg.afrimeta.profile.Profile;
import com.msr.cg.afrimeta.profile.dto.ProfileDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Component
public class ProfileDtoToProfileConverter implements Converter<ProfileDto, Profile> {
    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public Profile convert(ProfileDto source) {
        return new Profile(
                source.profileId(),
                source.nom(),
                source.prenom(),
                source.numeroTelephone(),
                LocalDate.parse(source.dateNaissance()),
                source.genre(),
                source.addresse(),
                source.ville(),
                source.codePostal(),
                source.pays(),
                source.profilePicturePrl(),
                source.bio()
        );
    }
}
