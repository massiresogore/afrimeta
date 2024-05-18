package com.msr.cg.afrimeta.ville.converter;

import com.msr.cg.afrimeta.ville.Ville;
import com.msr.cg.afrimeta.ville.dto.VilleDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class VilleDtoToVilleConverter implements Converter<VilleDto, Ville> {
    @Override
    public Ville convert(VilleDto source) {
        return  new Ville(source.villeId(),source.nom());
    }
}
