package com.msr.cg.afrimeta.facture.converter;

import com.msr.cg.afrimeta.facture.Facture;
import com.msr.cg.afrimeta.facture.dto.FactureDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FactureDtoToFactureConverter implements Converter<FactureDto, Facture> {

    @Override
    public Facture convert(FactureDto source) {
       return new Facture(
                source.factureDate(),
                source.totalHorsTaxe(),
                source.totalToutTaxeComprise(),
                source.totalTva()
        );
    }
}
