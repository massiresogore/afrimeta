package com.msr.cg.afrimeta.produit;

//import com.msr.cg.afrimeta.image.Image;
import com.msr.cg.afrimeta.categorie.Categorie;
import com.msr.cg.afrimeta.couleur.Couleur;
import com.msr.cg.afrimeta.image.Image;
import com.msr.cg.afrimeta.produit.dto.dto.ProduitRequest;
import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import com.msr.cg.afrimeta.taille.Taille;
import com.msr.cg.afrimeta.typeproduit.TypeProduit;
import com.msr.cg.afrimeta.utils.AfrimetaCrudInterface;
import com.msr.cg.afrimeta.website.Website;
import com.msr.cg.afrimeta.website.WebsiteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProduitService implements AfrimetaCrudInterface<Produit> {
    private final WebsiteService websiteService;

    private final ProduitRepository repository;

    public ProduitService(WebsiteService websiteService, ProduitRepository repository) {
        this.websiteService = websiteService;
        this.repository = repository;
    }

    @Override
    public List<Produit> findAll() {
        return this.repository.findAll();
    }

    public List<Produit> selectProduitWithCategorieAndTypeProduitAndImagesByWebsiteId(String websiteId) {
        return this.repository.selectProduitWithCategorieAndTypeProduitAndImagesByWebsiteId(Long.parseLong(websiteId));
    }

    public Produit singleProduitByProduitId (String produitId) throws ObjectNotFoundException {
        return this.repository.singleProduitWithCategorieAndTypeProduitByProduitId(Integer.parseInt(produitId));
    }

    @Override
    public Produit findById(Long id) {
        return this.repository.findById(id)
                .orElseThrow(()->new ObjectNotFoundException(Produit.class.getSimpleName(),id));
    }

    @Override
    public Produit save(Produit produit) {
        return null;
    }

    public Produit save(ProduitRequest produitRequest) {
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

        //Image
        produit.addImage(new Image());
        Produit savedProduit = null;
        if (website != null) {
            produit.setWebsite(website);
          savedProduit =  this.repository.save(produit);
        }
        return savedProduit;
    }

    @Override
    public Produit update(Produit newProduit, Long id) {
        return this.repository.findById(id)
                .map(oldProduit->{
                    oldProduit.setTitre(newProduit.getTitre());
                    oldProduit.setDescription(newProduit.getDescription());
                    oldProduit.setQuantiteStock(newProduit.getQuantiteStock());
                    oldProduit.setPrix(newProduit.getPrix());
                    oldProduit.setCategorie(newProduit.getCategorie());
                    oldProduit.setTypeProduit(newProduit.getTypeProduit());
                    return this.repository.save(oldProduit);
                })
                .orElseThrow(()->new ObjectNotFoundException(Produit.class.getSimpleName(),id));
    }

    @Override
    public void deleteById(Long id) {
        this.findById(id);
        this.repository.deleteById(id);

    }

    @Override
    public void delete(Produit produit) {
        this.findById(produit.produitId);
        this.repository.delete(produit);
    }

    public Page<Produit> findAllPageable(Pageable pageable) {
       return this.repository.findAllProduitWithPagination(pageable);
    }

}
