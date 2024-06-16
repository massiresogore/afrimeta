package com.msr.cg.afrimeta.paiement.converter;

import com.msr.cg.afrimeta.commande.converter.CommandeToCommandeDtoConverter;
import com.msr.cg.afrimeta.modepaiement.converter.ModePaiementToModePaiementDtoConverter;
import com.msr.cg.afrimeta.modepaiement.dto.ModePaiementDto;
import com.msr.cg.afrimeta.paiement.Paiement;
import com.msr.cg.afrimeta.paiement.dto.PaiementDto;
import com.msr.cg.afrimeta.utils.AfrimetaUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaiementToPaiementDtoConverter implements Converter<Paiement, PaiementDto> {

    private final CommandeToCommandeDtoConverter commandeToCommandeDtoConverter;
    private final ModePaiementToModePaiementDtoConverter modePaiementToModePaiementDtoConverter;

    public PaiementToPaiementDtoConverter(CommandeToCommandeDtoConverter commandeToCommandeDtoConverter, ModePaiementToModePaiementDtoConverter modePaiementToModePaiementDtoConverter) {
        this.commandeToCommandeDtoConverter = commandeToCommandeDtoConverter;
        this.modePaiementToModePaiementDtoConverter = modePaiementToModePaiementDtoConverter;
    }

    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public PaiementDto convert(Paiement source) {

        return new PaiementDto(
                source.getPaimementId(),
                source.getPaiementDate().format(AfrimetaUtils.EuropeanDateTimeFormatter()),
                source.getDescription(),
                this.modePaiementToModePaiementDtoConverter.convert(source.getModePaiement()),
                this.commandeToCommandeDtoConverter.convert(source.getCommande())
        );
    }

    public List<PaiementDto> convert(List<Paiement> sources) {

       return sources.stream().map(source-> new PaiementDto(
                 source.getPaimementId(),
                 source.getPaiementDate().format(AfrimetaUtils.EuropeanDateTimeFormatter()),
                 source.getDescription(),
                 this.modePaiementToModePaiementDtoConverter.convert(source.getModePaiement()),
                 this.commandeToCommandeDtoConverter.convert(source.getCommande())
         )).toList();
    }
}
