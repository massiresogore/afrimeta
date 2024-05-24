package com.msr.cg.afrimeta.produit.converter;

import com.msr.cg.afrimeta.produit.Produit;
import com.msr.cg.afrimeta.produit.dto.ProduitDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProduitDtoToProduitConverter implements Converter<ProduitDto, Produit> {

    @Override
    public Produit convert(ProduitDto source) {
        return new Produit(
                source.produitId(),
                source.tite(),
                source.description(),
                source.quantiteStock(),
                source.imageUrl(),
                source.prix(),
                source.dateAjout(),
                source.categorie(),
                source.typeProduit(),
                source.website()
        );
    }
}
