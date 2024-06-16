package com.msr.cg.afrimeta.livraison.converter;

import com.msr.cg.afrimeta.commande.converter.CommandeToCommandeDtoConverter;

import com.msr.cg.afrimeta.livraison.Livraison;
import com.msr.cg.afrimeta.livraison.dto.LivraisonDto;
import com.msr.cg.afrimeta.modelivraison.converter.ModeLivraisonToModeLivraisonDtoConverter;
import com.msr.cg.afrimeta.utils.AfrimetaUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LivraisonToLivraisonDtoConverter implements Converter<Livraison, LivraisonDto> {

    private final CommandeToCommandeDtoConverter commandeToCommandeDtoConverter;
    private final ModeLivraisonToModeLivraisonDtoConverter modeLivraisonToModeLivraisonDtoConverter;

    public LivraisonToLivraisonDtoConverter(CommandeToCommandeDtoConverter commandeToCommandeDtoConverter, ModeLivraisonToModeLivraisonDtoConverter modeLivraisonToModeLivraisonDtoConverter) {
        this.commandeToCommandeDtoConverter = commandeToCommandeDtoConverter;
        this.modeLivraisonToModeLivraisonDtoConverter = modeLivraisonToModeLivraisonDtoConverter;
    }

    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public LivraisonDto convert(Livraison source) {

        return new LivraisonDto(
                source.getPaimementId(),
                source.getLivraisonDate().format(AfrimetaUtils.EuropeanDateTimeFormatter()),
                source.getDescription(),
                this.modeLivraisonToModeLivraisonDtoConverter.convert(source.getModeLivraison()),
                this.commandeToCommandeDtoConverter.convert(source.getCommande())
        );
    }

    public List<LivraisonDto> convert(List<Livraison> sources) {

       return sources.stream().map(source-> new LivraisonDto(
                 source.getPaimementId(),
                 source.getLivraisonDate().format(AfrimetaUtils.EuropeanDateTimeFormatter()),
                 source.getDescription(),
                 this.modeLivraisonToModeLivraisonDtoConverter.convert(source.getModeLivraison()),
                 this.commandeToCommandeDtoConverter.convert(source.getCommande())
         )).toList();
    }
}
