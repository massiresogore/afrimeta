package com.msr.cg.afrimeta.magasin.converter;

import com.msr.cg.afrimeta.magasin.Magasin;
import com.msr.cg.afrimeta.magasin.dto.MagasinResponse;
import com.msr.cg.afrimeta.magasin.dto.MagasinDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MagasinToMagasinDtoConverter implements Converter<Magasin, MagasinDto> {
    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public MagasinDto convert(Magasin source) {
        return new MagasinDto(
                source.getMagasinId(),
                source.getLibele(),
                source.getDescription(),
                source.getLogo(),
                source.getClientUser()
        );
    }

    public MagasinResponse convertToResponse(Magasin source) {
        return new MagasinResponse(
                source.getMagasinId(),
                source.getLibele(),
                source.getDescription(),
                source.getLogo(),
                source.getClientUser().getUser_id()
        );
    }
}
