package com.msr.cg.afrimeta.commande.converter;

import com.msr.cg.afrimeta.clientUser.converter.ClientUserDtoToClientUserConverter;
import com.msr.cg.afrimeta.commande.Commande;
import com.msr.cg.afrimeta.commande.dto.CommandeDto;
import com.msr.cg.afrimeta.facture.converter.FactureDtoToFactureConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CommndeDtoToCommandeConverter implements Converter<CommandeDto, Commande> {
    private final ClientUserDtoToClientUserConverter clientUserDtoToClientUserConverter;

    public CommndeDtoToCommandeConverter(ClientUserDtoToClientUserConverter clientUserDtoToClientUserConverter ) {
        this.clientUserDtoToClientUserConverter = clientUserDtoToClientUserConverter;
    }

    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public Commande convert(CommandeDto source) {
        return new Commande(
                source.createdAt(),
                LocalDateTime.parse(source.updatedAt()),
                source.commandeTotal(),
                source.adresse(),
                source.prixTotal(),
                source.nombreProduit(),
                this.clientUserDtoToClientUserConverter.convert(source.clientUser()));
    }
}
