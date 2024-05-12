package com.msr.cg.afrimeta.ville.converter;

import com.msr.cg.afrimeta.ville.Ville;
import com.msr.cg.afrimeta.ville.dto.VilleDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class VilleToVilleDtoConverter implements Converter<Ville, VilleDto> {
    @Override
    public VilleDto convert(Ville source) {
        return new VilleDto(source.getVilleId(),source.getNom());
    }
}
