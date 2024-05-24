package com.msr.cg.afrimeta.produit.converter;

import com.msr.cg.afrimeta.produit.Produit;
import com.msr.cg.afrimeta.produit.dto.ProduitDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProduitToProduitDtoConverter implements Converter<Produit, ProduitDto> {

    @Override
    public ProduitDto convert(Produit source) {

        return new ProduitDto(
                source.getProduitId(),
                source.getTite(),
                source.getDescription(),
                source.getQuantiteStock(),
                source.getImageUrl(),
                source.getPrix(),
                source.getDateAjout(),
                source.getCategorie(),
                source.getTypeProduit(),
                source.getWebsite()
        );
    }
}
