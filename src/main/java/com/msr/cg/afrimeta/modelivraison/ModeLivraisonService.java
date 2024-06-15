package com.msr.cg.afrimeta.modelivraison;

import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import com.msr.cg.afrimeta.utils.AfrimetaCrudInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModeLivraisonService implements AfrimetaCrudInterface<ModeLivraison> {
    private final ModeLivraisonRepository repository;

    public ModeLivraisonService(ModeLivraisonRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ModeLivraison> findAll() {
        return this.repository.findAll();
    }

    @Override
    public ModeLivraison findById(Long modeLivraisonId) {
        return this.repository.findById(modeLivraisonId).orElseThrow(() -> new ObjectNotFoundException("modeLivraison",modeLivraisonId));
    }

    @Override
    public ModeLivraison save(ModeLivraison modeLivraison) {
        return this.repository.save(modeLivraison);
    }

    @Override
    public ModeLivraison update(ModeLivraison modeLivraison, Long modeLivraisonId) {
        return this.repository.findById(modeLivraisonId)
                .map(oldModeLivraison->{
                    oldModeLivraison.setNom(modeLivraison.getNom());
                    return this.repository.save(oldModeLivraison);
                })
                .orElseThrow(() -> new ObjectNotFoundException("modeLivraison",modeLivraisonId));
    }

    @Override
    public void deleteById(Long oldModeLivraison) {
        this.findById(oldModeLivraison);
        this.repository.deleteById(oldModeLivraison);
    }

    @Override
    public void delete(ModeLivraison modeLivraison) {
        this.repository.delete(modeLivraison);
    }
}
