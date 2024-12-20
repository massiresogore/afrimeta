package com.msr.cg.afrimeta.taille.converter;

import com.msr.cg.afrimeta.taille.Taille;
import com.msr.cg.afrimeta.taille.dto.TailleDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TailleToTailleDtoConverter implements Converter<Taille, TailleDto> {

    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public TailleDto convert(Taille source) {
        return new TailleDto(source.getTailleId(),source.getNom());
    }
}
