package com.msr.cg.afrimeta.categorie;

import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import com.msr.cg.afrimeta.utils.AfrimetaCrudInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorieService implements AfrimetaCrudInterface<Categorie> {
    private final CategorieRepository repository;

    public CategorieService(CategorieRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Categorie> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Categorie findById(Long categorieId) {
        return repository.findById(categorieId).orElseThrow(() -> new ObjectNotFoundException("categorie",categorieId));
    }

    @Override
    public Categorie save(Categorie categorie) {
        return repository.save(categorie);
    }

    @Override
    public Categorie update(Categorie categorie, Long categorieId) {
        return this.repository.findById(categorieId)
                .map(oldCategorie->{
                    oldCategorie.setNom(categorie.getNom());
                    return repository.save(oldCategorie);
                })
                .orElseThrow(() -> new ObjectNotFoundException("categorie",categorieId));
    }

    @Override
    public void deleteById(Long oldCategorie) {
        this.findById(oldCategorie);
        this.repository.deleteById(oldCategorie);
    }

    @Override
    public void delete(Categorie categorie) {
        this.repository.delete(categorie);
    }
}
