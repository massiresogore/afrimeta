package com.msr.cg.afrimeta.produit.dto.converter;

import com.msr.cg.afrimeta.categorie.Categorie;
import com.msr.cg.afrimeta.couleur.Couleur;
import com.msr.cg.afrimeta.image.Image;
import com.msr.cg.afrimeta.produit.Produit;
import com.msr.cg.afrimeta.produit.ProduitService;
import com.msr.cg.afrimeta.produit.dto.dto.ProduitDto;
import com.msr.cg.afrimeta.produit.dto.dto.ProduitRequest;
import com.msr.cg.afrimeta.produit.dto.dto.ProduitResponse;
import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import com.msr.cg.afrimeta.system.exception.WebsiteNotFoundException;
import com.msr.cg.afrimeta.taille.Taille;
import com.msr.cg.afrimeta.typeproduit.TypeProduit;
import com.msr.cg.afrimeta.website.Website;
import com.msr.cg.afrimeta.website.WebsiteService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProduitDtoToProduitConverter implements Converter<ProduitDto, Produit> {
    private final WebsiteService websiteService;
    private final ProduitService produitService;

    public ProduitDtoToProduitConverter(WebsiteService websiteService, ProduitService produitService) {
        this.websiteService = websiteService;
        this.produitService = produitService;
    }

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

    public Produit convert(ProduitRequest produitRequest) {
        Produit produit = new Produit();
        produit.setTitre(produitRequest.titre());
        produit.setDescription(produitRequest.description());
        produit.setQuantiteStock(produitRequest.quantiteStock());
        produit.setPrix(produitRequest.prix());

        //Catégorie
        Categorie defaultCategorie = new Categorie("default categorie");

        //Type produit and taille
        TypeProduit defaultTypeProduit = new TypeProduit("default type produit");
        Taille defaultTaille = new Taille("default taille");
        defaultTypeProduit.addTaille(defaultTaille);

        //Couleur
        Couleur defaultCouleur = new Couleur("default couleur");
        produit.setCategorie(defaultCategorie);
        produit.setTypeProduit(defaultTypeProduit);
        produit.addCouleur(defaultCouleur);

        //Find Website
        Website website = this.websiteService.findById(Long.valueOf(produitRequest.websiteId()));

        if (website == null) {
            throw new WebsiteNotFoundException("Nous ne pouvons pas enregistrer un produit n'appartenant a aucun besite");
        }
        produit.setWebsite(website);

        //Image
        produit.addImage(new Image());

        return produit;

    }

    public Produit convert(ProduitResponse produitResponse) {
        return this.produitService.findById(produitResponse.produitId());
    }
}
