package com.msr.cg.afrimeta.paiement.converter;

import com.msr.cg.afrimeta.commande.Commande;
import com.msr.cg.afrimeta.commande.CommandeService;
import com.msr.cg.afrimeta.modepaiement.ModePaiement;
import com.msr.cg.afrimeta.modepaiement.ModePaiementService;
import com.msr.cg.afrimeta.paiement.Paiement;
import com.msr.cg.afrimeta.paiement.PaiementService;
import com.msr.cg.afrimeta.paiement.dto.PaiementRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PaiementRequestToPaiement implements Converter<PaiementRequest, Paiement> {

    private final ModePaiementService modePaiementService;
    private final CommandeService commandeService;

    public PaiementRequestToPaiement(ModePaiementService modePaiementService, CommandeService commandeService) {
        this.modePaiementService = modePaiementService;
        this.commandeService = commandeService;
    }

    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public Paiement convert(PaiementRequest source) {
        Commande commande = this.commandeService.findById(Long.parseLong(source.commandeId()));
        ModePaiement modePaiement = this.modePaiementService.findById(Long.parseLong(source.modepaiementId()));
        return new Paiement(LocalDateTime.now(), source.description(),modePaiement,commande);

    }
}
