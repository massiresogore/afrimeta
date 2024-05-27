package com.msr.cg.afrimeta.produit.dto.converter;

import com.msr.cg.afrimeta.produit.Produit;
import com.msr.cg.afrimeta.produit.dto.dto.ProduitDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProduitDtoToProduitConverter implements Converter<ProduitDto, Produit> {
    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public Produit convert(ProduitDto source) {
        return new Produit(
                source.produitId(),
                source.titre(),
                source.description(),
                source.quantiteStock(),
                source.prix(),
                source.dateAjout(),
                source.categorie(),
                source.typeProduit(),
                source.website()
        );
    }
}
