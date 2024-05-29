package com.msr.cg.afrimeta.couleur.converter;

import com.msr.cg.afrimeta.couleur.Couleur;
import com.msr.cg.afrimeta.couleur.dto.CouleurDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CouleurDtoToCouleurConverter implements Converter<CouleurDto, Couleur> {
    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public Couleur convert(CouleurDto source) {
        return new Couleur(source.couleurId(),source.nom());
    }
}