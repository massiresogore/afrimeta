package com.msr.cg.afrimeta.produit.dto;

import com.msr.cg.afrimeta.produit.Produit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProduitDtoSercice {

      public List<ProduitDtos> converterToProduitDtoList(List<Produit> produits) {
         return produits.stream().map(produit -> new ProduitDtos(
                  produit.getProduitId(),
                  produit.getTitre(),
                  produit.getDescription(),
                  produit.getQuantiteStock(),
                  produit.getImageUrl(),
                  produit.getPrix(),
                  produit.getDateAjout(),
                  produit.getCategorie(),
                  produit.getTypeProduit()
          )).toList();
      }

      public ProduitDtos converterToProduitDto(Produit produit) {
         return  new ProduitDtos(
                  produit.getProduitId(),
                  produit.getTitre(),
                  produit.getDescription(),
                  produit.getQuantiteStock(),
                  produit.getImageUrl(),
                  produit.getPrix(),
                  produit.getDateAjout(),
                  produit.getCategorie(),
                  produit.getTypeProduit()
          );
      }
}
