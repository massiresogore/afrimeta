package com.msr.cg.afrimeta.couleur;

import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import com.msr.cg.afrimeta.utils.AfrimetaCrudInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouleurService implements AfrimetaCrudInterface<Couleur> {
    private final CouleurRepository repository;

    public CouleurService(CouleurRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Couleur> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Couleur findById(Long couleurId) {
        return repository.findById(couleurId).orElseThrow(() -> new ObjectNotFoundException("couleur",couleurId));
    }

    @Override
    public Couleur save(Couleur couleur) {
        return repository.save(couleur);
    }

    @Override
    public Couleur update(Couleur couleur, Long couleurId) {
        return this.repository.findById(couleurId)
                .map(oldCouleur->{
                    oldCouleur.setNom(couleur.getNom());
                    return repository.save(oldCouleur);
                })
                .orElseThrow(() -> new ObjectNotFoundException("couleur",couleurId));
    }

    @Override
    public void deleteById(Long oldCouleur) {
        this.findById(oldCouleur);
        this.repository.deleteById(oldCouleur);
    }

    @Override
    public void delete(Couleur couleur) {
        this.repository.delete(couleur);
    }
}
