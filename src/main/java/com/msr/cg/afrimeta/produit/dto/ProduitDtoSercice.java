package com.msr.cg.afrimeta.produit.dto;

import com.msr.cg.afrimeta.couleur.Couleur;
import com.msr.cg.afrimeta.image.ImageService;
import com.msr.cg.afrimeta.produit.Produit;
import com.msr.cg.afrimeta.produit.ProduitService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProduitDtoSercice {
    private final ProduitService produitService;
    private final ImageService imageService;

    public ProduitDtoSercice(ProduitService produitService, ImageService imageService) {
        this.produitService = produitService;
        this.imageService = imageService;
    }

      public List<ProduitDto> converterToProduitDtoList(List<Produit> produits) {

         return produits.stream().map(produit -> new ProduitDto(
                  produit.getProduitId(),
                  produit.getTitre(),
                  produit.getDescription(),
                  produit.getQuantiteStock(),
                  produit.getPrix(),
                  produit.getDateAjout(),
                 produit.getCategorie(),
                 produit.getTypeProduit(),
                 produit.getWebsite(),
                 produit.getCouleurs(),
                 (String[]) produit.getImages().toArray()
         )).toList();
      }


//      /*
//      private Website website;
//
//    private Set<Couleur> couleur;
//
//    private Set<Image> images;*/
/*
      public ProduitDto produitDto(Produit produit) {
         return  new ProduitDto(
                  produit.getProduitId(),
                  produit.getTitre(),
                  produit.getDescription(),
                  produit.getQuantiteStock(),
                  produit.getPrix(),
                  produit.getDateAjout(),
                  produit.getCategorie(),
                  produit.getTypeProduit());
      }*/
}
