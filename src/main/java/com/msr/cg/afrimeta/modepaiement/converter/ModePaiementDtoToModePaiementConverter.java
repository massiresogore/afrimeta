package com.msr.cg.afrimeta.modepaiement.converter;
import com.msr.cg.afrimeta.modepaiement.ModePaiement;
import com.msr.cg.afrimeta.modepaiement.dto.ModePaiementDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ModePaiementDtoToModePaiementConverter implements Converter<ModePaiementDto, ModePaiement> {

    @Override
    public ModePaiement convert(ModePaiementDto source) {
        return new ModePaiement(source.modePaiementId(),source.nom());
    }
}

