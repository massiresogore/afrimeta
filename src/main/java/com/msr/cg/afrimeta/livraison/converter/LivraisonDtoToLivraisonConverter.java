package com.msr.cg.afrimeta.livraison.converter;

import com.msr.cg.afrimeta.commande.CommandeService;
import com.msr.cg.afrimeta.commande.converter.CommandeDtoToCommandeConverter;

import com.msr.cg.afrimeta.livraison.Livraison;
import com.msr.cg.afrimeta.livraison.dto.LivraisonDto;
import com.msr.cg.afrimeta.modelivraison.ModeLivraisonService;
import com.msr.cg.afrimeta.modelivraison.converter.ModeLivraisonDtoToModeLivraisonConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LivraisonDtoToLivraisonConverter implements Converter<LivraisonDto, Livraison> {
    private final ModeLivraisonDtoToModeLivraisonConverter modeLivraisonDtoToModeLivraisonConverter;
    private final CommandeDtoToCommandeConverter commandeDtoToCommandeConverter;
    private final ModeLivraisonService modeLivraisonService;
    private  final CommandeService commandeService;

    public LivraisonDtoToLivraisonConverter(ModeLivraisonDtoToModeLivraisonConverter modeLivraisonDtoToModeLivraisonConverter, CommandeDtoToCommandeConverter commandeDtoToCommandeConverter, ModeLivraisonService modeLivraisonService, CommandeService commandeService) {
        this.modeLivraisonDtoToModeLivraisonConverter = modeLivraisonDtoToModeLivraisonConverter;
        this.commandeDtoToCommandeConverter = commandeDtoToCommandeConverter;
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
    public Livraison convert(LivraisonDto source) {
        return new Livraison(
                source.livraisonId(),
                LocalDateTime.now(),
                source.description(),
                this.modeLivraisonDtoToModeLivraisonConverter.convert(source.modeLivraison()),
                this.commandeDtoToCommandeConverter.convert(source.commande())
        );
    }


  /*  public Livraison convert(LivraisonRequest source) {
        return new Livraison(
                source.livraisonId(),
                source.paiementDate(),
                source.description(),
                this.modeLivraisonService.findById(Long.valueOf(source.modeLivraisonId())),
                this.commandeService.findById(Long.parseLong(source.commandeId()))
        );
    }*/

    public Livraison convert(LivraisonDto source, String modeLivraisonId, String commandeId) {

      return new Livraison(
              source.livraisonId(),
              LocalDateTime.now(),
              source.description(),
              this.modeLivraisonService.findById(Long.valueOf(modeLivraisonId)),
              this.commandeService.findById(Long.parseLong(commandeId))
        );
    }

}
