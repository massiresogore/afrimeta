package com.msr.cg.afrimeta.commande;

import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import com.msr.cg.afrimeta.utils.AfrimetaCrudInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandeService implements AfrimetaCrudInterface<Commande> {

    private final CommandeRepository repository;

    public CommandeService(CommandeRepository repository) {
        this.repository = repository;
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
                    oldCommande.setCommandeDate(commande.getCommandeDate());
                    oldCommande.setCommandeTotal(commande.getCommandeTotal());
                    oldCommande.setAdresse(commande.getAdresse());
                    oldCommande.setFacture(commande.getFacture());
                    oldCommande.setClientUser(commande.getClientUser());
                    oldCommande.setPrixTotal(commande.getPrixTotal());
                    oldCommande.setNombreProduit(commande.getNombreProduit());
                    return this.repository.save(oldCommande);
                })
                .orElseThrow(()-> new ObjectNotFoundException(Commande.class.getSimpleName(), id));
    }

    @Override
    public void deleteById(Long id) {
        this.findById(id);
        this.repository.deleteById(id);
    }

    @Override
    public void delete(Commande commande) {
        this.repository.delete(commande);
    }
}
