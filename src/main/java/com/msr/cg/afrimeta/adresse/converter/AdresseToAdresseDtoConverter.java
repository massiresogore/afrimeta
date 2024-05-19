package com.msr.cg.afrimeta.adresse.converter;

import com.msr.cg.afrimeta.adresse.Adresse;
import com.msr.cg.afrimeta.adresse.dto.AdresseDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
public class AdresseToAdresseDtoConverter implements Converter<Adresse, AdresseDto> {
    @Override
    public AdresseDto convert(Adresse source) {

        return new AdresseDto(
                source.getAdresseId(),
                source.getNumero(),
                source.getCp(),
                source.getVille());
    }
}
