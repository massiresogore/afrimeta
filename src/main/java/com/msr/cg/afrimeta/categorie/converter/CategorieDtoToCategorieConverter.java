package com.msr.cg.afrimeta.categorie.converter;

import com.msr.cg.afrimeta.categorie.Categorie;
import com.msr.cg.afrimeta.categorie.dto.CategorieDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategorieDtoToCategorieConverter implements Converter<CategorieDto, Categorie> {
    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public Categorie convert(CategorieDto source) {
        return new Categorie(source.categorieId(),source.nom());
    }
}
