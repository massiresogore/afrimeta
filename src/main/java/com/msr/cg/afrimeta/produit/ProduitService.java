package com.msr.cg.afrimeta.produit;

import com.msr.cg.afrimeta.couleur.Couleur;
import com.msr.cg.afrimeta.produit.dto.ProduitDto;
import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import com.msr.cg.afrimeta.utils.AfrimetaCrudInterface;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProduitService implements AfrimetaCrudInterface<Produit> {

    private final ProduitRepository repository;

    public ProduitService(ProduitRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Produit> findAll() {
        return this.repository.findAll();
    }

    public List<Produit> selectProduitByWebsiteId(String websiteId) {
        return this.repository.selectProduitWithCategorieAndTypeProduitByWebsiteId(Long.parseLong(websiteId));
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

        return this.repository.save(produit);
    }

    public Produit save(Produit produit, String[] couleurValues) {
        if (couleurValues != null) {

            for (String value : couleurValues) {
              Couleur  saveCouleur = new Couleur(value);
              produit.addCouleur(saveCouleur);
            }
        }
        return this.repository.save(produit);
    }

    @Override
    public Produit update(Produit newProduit, Long id) {
        return this.repository.findById(id)
                .map(oldProduit->{
                    oldProduit.setTitre(newProduit.getTitre());
                    oldProduit.setDescription(newProduit.getDescription());
                    oldProduit.setQuantiteStock(newProduit.getQuantiteStock());
                    oldProduit.setImageUrl(newProduit.getImageUrl());
                    oldProduit.setPrix(newProduit.getPrix());
                    oldProduit.setCategorie(newProduit.getCategorie());
                    oldProduit.setTypeProduit(newProduit.getTypeProduit());
                    oldProduit.setWebsite(newProduit.getWebsite());
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



}
