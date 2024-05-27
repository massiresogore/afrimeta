package com.msr.cg.afrimeta.produit;

import com.msr.cg.afrimeta.categorie.Categorie;
import com.msr.cg.afrimeta.couleur.Couleur;
import com.msr.cg.afrimeta.image.Image;
import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import com.msr.cg.afrimeta.typeproduit.TypeProduit;
import com.msr.cg.afrimeta.utils.AfrimetaCrudInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProduitService implements AfrimetaCrudInterface<Produit> {

    private final ProduitRepository repository;

    public ProduitService( ProduitRepository repository) {
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
                Produit p = this.repository.singleProduitWithCategorieAndTypeProduitByProduitId(Integer.parseInt(produitId));
        return this.repository.singleProduitWithCategorieAndTypeProduitByProduitId(Integer.parseInt(produitId));
    }

    @Override
    public Produit findById(Long id) {
        return this.repository.findById(id)
                .orElseThrow(()->new ObjectNotFoundException(Produit.class.getSimpleName(),id));
    }

    @Override
    public Produit save(Produit produit) {

        Categorie defaultCategorie = new Categorie("default categorie");
        TypeProduit defaultTypeProduit = new TypeProduit("default type produit");
        Couleur defaultCouleur = new Couleur("default couleur");
        Image defaultImage = new Image("image/png","/Users/esprit/www_java/projet_personnel_b3/afrimeta/afrimeta_server/src/main/resources/uploads/defaultImage.png","defaultImage.png",produit);

        produit.setCategorie(defaultCategorie);
        produit.setTypeProduit(defaultTypeProduit);
        produit.addImage(defaultImage);
        produit.addCouleur(defaultCouleur);

        return this.repository.save(produit);
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

    @Transactional(readOnly = true)
    public Page<Produit> findAllPageable(Pageable pageable) {

//       return this.repository.findAll(pageable);
       return this.repository.findAllProduitWithPagination(pageable);
    }



}
