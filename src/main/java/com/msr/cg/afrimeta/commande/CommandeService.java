package com.msr.cg.afrimeta.commande;

import com.msr.cg.afrimeta.embaddable.Contenir;
import com.msr.cg.afrimeta.embaddable.ContenirRepository;
import com.msr.cg.afrimeta.embaddable.ProduitCommandeKey;
import com.msr.cg.afrimeta.produit.Produit;
import com.msr.cg.afrimeta.produit.ProduitRepository;
import com.msr.cg.afrimeta.produit.ProduitService;
import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import com.msr.cg.afrimeta.utils.AfrimetaCrudInterface;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommandeService implements AfrimetaCrudInterface<Commande> {

    private final CommandeRepository repository;
    private final ProduitService  produitService;
    private final ContenirRepository contenirRepository;

    public CommandeService(CommandeRepository repository, ProduitService produitService, ContenirRepository contenirRepository) {
        this.repository = repository;
        this.produitService = produitService;
        this.contenirRepository = contenirRepository;
    }

    @Override
    public List<Commande> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Commande findById(Long id) {
        return this.repository.findById(id).orElseThrow(()-> new ObjectNotFoundException(Commande.class.getSimpleName(), id));
    }

    @Override
    public Commande save(Commande commande) {
        return this.repository.save(commande);
    }

    @Override
    public Commande update(Commande commande, Long id) {

        return this.repository.findById(id)
                .map(oldCommande->{
//                    oldCommande.setCreatedAt(commande.getCreatedAt()!=null ?commande.getCreatedAt(): oldCommande.getCreatedAt());
                    oldCommande.setUpdatedAt(LocalDateTime.now());
                    oldCommande.setCommandeTotal(commande.getCommandeTotal());
                    oldCommande.setAdresse(commande.getAdresse());
//                    oldCommande.setFacture(commande.getFacture());
                    oldCommande.setClientUser(commande.getClientUser());
                    oldCommande.setPrixTotal(commande.getPrixTotal());
                    oldCommande.setNombreProduit(commande.getNombreProduit());
                    return this.repository.save(oldCommande);
                })
                .orElseThrow(()-> new ObjectNotFoundException(Commande.class.getSimpleName(), id));
    }

    @Override
    public void deleteById(Long id) {
       Commande commande = this.findById(id);
        commande.setClientUser(null);
        this.delete(commande);
    }

    @Override
    public void delete(Commande commande) {
        this.repository.delete(commande);
    }

    public Commande save(Commande newComande, List<String> strings) {

        List<Produit> produits = new ArrayList<>();
        strings.forEach(s -> {
            Produit p = this.produitService.findById(Long.parseLong(s));
            produits.add(p);
        });

        //SavedCommende
        Commande savedCommande = this.repository.save(newComande);


        //Loop over produit to save into contenir table
        for(Produit produit: produits) {
            Contenir contenir = new Contenir();
            contenir.setCommande(savedCommande);
            contenir.setQuantite(produit.getQuantiteStock());
            contenir.setProduit(produit);
            ProduitCommandeKey produitCommandeKey = new ProduitCommandeKey(produit.getProduitId(), savedCommande.getCommandeId());
            contenir.setId(produitCommandeKey);
            contenirRepository.save(contenir);
        }

        //System.out.println(produits);

        return  savedCommande;
    }
}
