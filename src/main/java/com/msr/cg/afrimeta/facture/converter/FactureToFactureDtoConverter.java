package com.msr.cg.afrimeta.facture.converter;

import com.msr.cg.afrimeta.facture.Facture;
import com.msr.cg.afrimeta.facture.dto.FactureDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FactureToFactureDtoConverter implements Converter<Facture, FactureDto> {

    @Override
    public FactureDto convert(Facture source) {
      return new FactureDto(
                source.getFactureId(),
                source.getFactureDate(),
                source.getTotalHorsTaxe(),
                source.getTotalToutTaxeComprise(),
                source.getTotalTva()
        );
    }
}
