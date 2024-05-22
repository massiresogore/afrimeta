package com.msr.cg.afrimeta.typeproduit.converter;

import com.msr.cg.afrimeta.typeproduit.TypeProduit;
import com.msr.cg.afrimeta.typeproduit.dto.TypeProduitDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TypeProduitToTypeProduitDtoConverter implements Converter<TypeProduit, TypeProduitDto> {

    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public TypeProduitDto convert(TypeProduit source) {
        return new TypeProduitDto(source.getTypeProduitId(),source.getNom());
    }
}