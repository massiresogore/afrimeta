package com.msr.cg.afrimeta.paiement.converter;

import com.msr.cg.afrimeta.commande.Commande;
import com.msr.cg.afrimeta.commande.CommandeService;
import com.msr.cg.afrimeta.commande.converter.CommandeDtoToCommandeConverter;
import com.msr.cg.afrimeta.commande.converter.CommandeToCommandeDtoConverter;
import com.msr.cg.afrimeta.modepaiement.ModePaiement;
import com.msr.cg.afrimeta.modepaiement.ModePaiementService;
import com.msr.cg.afrimeta.modepaiement.converter.ModePaiementDtoToModePaiementConverter;
import com.msr.cg.afrimeta.paiement.Paiement;
import com.msr.cg.afrimeta.paiement.dto.PaiementDto;
import com.msr.cg.afrimeta.paiement.dto.PaiementRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class PaiementDtoToPaiementConverter implements Converter<PaiementDto, Paiement> {
    private final ModePaiementDtoToModePaiementConverter modePaiementDtoToModePaiementConverter;
    private final CommandeDtoToCommandeConverter commandeDtoToCommandeConverter;
    private final ModePaiementService modePaiementService;
    private  final CommandeService commandeService;

    public PaiementDtoToPaiementConverter(ModePaiementDtoToModePaiementConverter modePaiementDtoToModePaiementConverter, CommandeDtoToCommandeConverter commandeDtoToCommandeConverter, ModePaiementService modePaiementService, CommandeService commandeService) {
        this.modePaiementDtoToModePaiementConverter = modePaiementDtoToModePaiementConverter;
        this.commandeDtoToCommandeConverter = commandeDtoToCommandeConverter;
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
    public Paiement convert(PaiementDto source) {
        return new Paiement(
                source.paimementId(),
                LocalDateTime.now(),
                source.description(),
                this.modePaiementDtoToModePaiementConverter.convert(source.modePaiement()),
                this.commandeDtoToCommandeConverter.convert(source.commande())
        );
    }


  /*  public Paiement convert(PaiementRequest source) {
        return new Paiement(
                source.paimementId(),
                source.paiementDate(),
                source.description(),
                this.modePaiementService.findById(Long.valueOf(source.modePaiementId())),
                this.commandeService.findById(Long.parseLong(source.commandeId()))
        );
    }*/

    public Paiement convert(PaiementDto source, String modePaiementId, String commandeId) {

      return new Paiement(
              source.paimementId(),
              LocalDateTime.now(),
              source.description(),
              this.modePaiementService.findById(Long.valueOf(modePaiementId)),
              this.commandeService.findById(Long.parseLong(commandeId))
        );
    }

}
