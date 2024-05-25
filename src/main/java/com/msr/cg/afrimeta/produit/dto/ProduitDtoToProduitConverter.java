package com.msr.cg.afrimeta.produit.dto;

import com.msr.cg.afrimeta.produit.Produit;
import org.springframework.stereotype.Component;

@Component
public class ProduitDtoToProduitConverter  {
    public Produit convert(ProduitDto source) {
        return new Produit(
                source.produitId(),
                source.titre(),
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
