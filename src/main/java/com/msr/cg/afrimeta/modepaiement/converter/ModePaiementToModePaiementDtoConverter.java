package com.msr.cg.afrimeta.modepaiement.converter;

import com.msr.cg.afrimeta.modepaiement.ModePaiement;
import com.msr.cg.afrimeta.modepaiement.dto.ModePaiementDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ModePaiementToModePaiementDtoConverter implements Converter<ModePaiement, ModePaiementDto> {

    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public ModePaiementDto convert(ModePaiement source) {
        return new ModePaiementDto(source.getModePaiementId(),source.getNom());
    }
}
