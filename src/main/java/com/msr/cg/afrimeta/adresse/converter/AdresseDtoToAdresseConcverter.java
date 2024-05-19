package com.msr.cg.afrimeta.adresse.converter;

import com.msr.cg.afrimeta.adresse.Adresse;
import com.msr.cg.afrimeta.adresse.dto.AdresseDto;
import com.msr.cg.afrimeta.ville.VilleRepository;
import com.msr.cg.afrimeta.ville.VilleService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
public class AdresseDtoToAdresseConcverter implements Converter<AdresseDto, Adresse> {

    @Override
    public Adresse convert(AdresseDto source) {

        return new Adresse(
                source.adresseId(),
                source.numero(),
                source.cp(),
                source.ville()
        );
    }
}
