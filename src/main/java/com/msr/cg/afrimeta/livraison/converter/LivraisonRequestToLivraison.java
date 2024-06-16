package com.msr.cg.afrimeta.livraison.converter;

import com.msr.cg.afrimeta.commande.Commande;
import com.msr.cg.afrimeta.commande.CommandeService;
import com.msr.cg.afrimeta.modelivraison.ModeLivraison;
import com.msr.cg.afrimeta.modelivraison.ModeLivraisonService;
import com.msr.cg.afrimeta.livraison.Livraison;
import com.msr.cg.afrimeta.livraison.dto.LivraisonRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LivraisonRequestToLivraison implements Converter<LivraisonRequest, Livraison> {

    private final ModeLivraisonService modeLivraisonService;
    private final CommandeService commandeService;

    public LivraisonRequestToLivraison(ModeLivraisonService modeLivraisonService, CommandeService commandeService) {
        this.modeLivraisonService = modeLivraisonService;
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
    public Livraison convert(LivraisonRequest source) {
        Commande commande = this.commandeService.findById(Long.parseLong(source.commandeId()));
        ModeLivraison modeLivraison = this.modeLivraisonService.findById(Long.parseLong(source.modelivraisonId()));
        return new Livraison(LocalDateTime.now(), source.description(),modeLivraison,commande);

    }
}
