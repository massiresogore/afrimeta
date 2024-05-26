package com.msr.cg.afrimeta.produit.dto.converter;

import com.msr.cg.afrimeta.produit.Produit;
import com.msr.cg.afrimeta.produit.dto.ProduitDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ProduitToProduitDtoConverter implements Converter<Produit, ProduitDto> {
    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public ProduitDto convert(Produit source) {
        String[] imageName = source.getImages().stream().map(image -> image.getName()).toArray(String[]::new);
        return new ProduitDto(
                source.getProduitId(),
                source.getTitre(),
                source.getDescription(),
                source.getQuantiteStock(),
                source.getPrix(),
                source.getDateAjout(),
                source.getCategorie(),
                source.getTypeProduit(),
                source.getWebsite(),
                source.getCouleurs(),
                imageName
        );
    }

    public List<ProduitDto> convert(List<Produit> source) {
        List<ProduitDto> produitDtos = new ArrayList<>();
        for (Produit produit : source) {
            produitDtos.add(this.convert(produit));
        }
        return produitDtos;
    }
}
