package com.msr.cg.afrimeta.typeproduit;

import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import com.msr.cg.afrimeta.utils.AfrimetaCrudInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeProduitService implements AfrimetaCrudInterface<TypeProduit> {
    private final TypeProduitRepository repository;

    public TypeProduitService(TypeProduitRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TypeProduit> findAll() {
        return this.repository.findAll();
    }

    @Override
    public TypeProduit findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Typeproduit",id));
    }

    @Override
    public TypeProduit save(TypeProduit typeProduit) {
        return repository.save(typeProduit);
    }

    @Override
    public TypeProduit update(TypeProduit typeProduit, Long id) {
        return this.repository.findById(id)
                .map(oldTypeProduit->{
                    oldTypeProduit.setNom(typeProduit.getNom());
                    return repository.save(oldTypeProduit);
                })
                .orElseThrow(() -> new ObjectNotFoundException("Typeproduit",id));
    }

    @Override
    public void deleteById(Long id) {
        this.findById(id);
        this.repository.deleteById(id);
    }

    @Override
    public void delete(TypeProduit typeProduit) {
        this.repository.delete(typeProduit);
    }
}
