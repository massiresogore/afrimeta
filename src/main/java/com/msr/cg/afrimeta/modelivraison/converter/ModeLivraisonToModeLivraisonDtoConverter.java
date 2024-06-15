package com.msr.cg.afrimeta.modelivraison.converter;

import com.msr.cg.afrimeta.modelivraison.ModeLivraison;
import com.msr.cg.afrimeta.modelivraison.dto.ModeLivraisonDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ModeLivraisonToModeLivraisonDtoConverter implements Converter<ModeLivraison, ModeLivraisonDto> {

    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public ModeLivraisonDto convert(ModeLivraison source) {
        return new ModeLivraisonDto(source.getModeLivraisonId(),source.getNom());
    }
}
