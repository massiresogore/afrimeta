package com.msr.cg.afrimeta.modelivraison.converter;
import com.msr.cg.afrimeta.modelivraison.ModeLivraison;
import com.msr.cg.afrimeta.modelivraison.dto.ModeLivraisonDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ModeLivraisonDtoToModeLivraisonConverter implements Converter<ModeLivraisonDto, ModeLivraison> {

    @Override
    public ModeLivraison convert(ModeLivraisonDto source) {
        return new ModeLivraison(source.modeLivraisonId(),source.nom());
    }
}

