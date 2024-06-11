package com.msr.cg.afrimeta.commande.converter;

import com.msr.cg.afrimeta.commande.Commande;
import com.msr.cg.afrimeta.commande.dto.CommandeDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CommandeToCommandeDtoConverter implements Converter<Commande, CommandeDto> {
    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public CommandeDto convert(Commande source) {
        return new CommandeDto(
                source.getCommandeId(),
                source.getCommandeDate(),
                source.getCommandeTotal(),
                source.getAdresse(),
                source.getPrixTotal(),
                source.getNombreProduit(),
                source.getClientUser(),
                source.getFacture()
        );
    }
}
