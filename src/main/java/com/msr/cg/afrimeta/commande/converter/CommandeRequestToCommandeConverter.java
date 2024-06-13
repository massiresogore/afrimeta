package com.msr.cg.afrimeta.commande.converter;

import com.msr.cg.afrimeta.clientUser.ClientUserService;
import com.msr.cg.afrimeta.commande.Commande;
import com.msr.cg.afrimeta.commande.request.CommandeRequest;
import com.msr.cg.afrimeta.facture.FactureService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CommandeRequestToCommandeConverter implements Converter<CommandeRequest, Commande> {

    private final ClientUserService userService;
    private final FactureService factureService;

    public CommandeRequestToCommandeConverter(ClientUserService userService, FactureService factureService) {
        this.userService = userService;
        this.factureService = factureService;
    }

    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public Commande convert(CommandeRequest source) {
       return new Commande(
                source.commandeDate(),
                source.commandeTotal(),
                source.adresse(),
                source.prixTotal(),
                source.nombreProduit(),
                this.userService.findById(Long.parseLong(source.clientUserId()))
        );
    }
}
