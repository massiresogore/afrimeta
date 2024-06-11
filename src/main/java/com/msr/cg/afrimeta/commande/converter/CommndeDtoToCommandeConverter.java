package com.msr.cg.afrimeta.commande.converter;

import com.msr.cg.afrimeta.commande.Commande;
import com.msr.cg.afrimeta.commande.dto.CommandeDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CommndeDtoToCommandeConverter implements Converter<CommandeDto, Commande> {
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
                source.commandeDate(),
                source.commandeTotal(),
                source.adresse(),
                source.prixTotal(),
                source.nombreProduit(),
                source.clientUser(),
                source.facture()
        );
    }
}
