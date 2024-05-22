package com.msr.cg.afrimeta.taille;

import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import com.msr.cg.afrimeta.utils.AfrimetaCrudInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TailleService implements AfrimetaCrudInterface<Taille> {
    private final TailleRepository repository;

    public TailleService(TailleRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Taille> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Taille findById(Long TailleId) {
        return repository.findById(TailleId).orElseThrow(() -> new ObjectNotFoundException("taille",TailleId));
    }

    @Override
    public Taille save(Taille taille) {
        return repository.save(taille);
    }

    @Override
    public Taille update(Taille taille, Long id) {
        return this.repository.findById(id)
                .map(oldTaille->{
                    taille.setNom(taille.getNom());
                    return repository.save(taille);
                })
                .orElseThrow(() -> new ObjectNotFoundException("taille",id));
    }

    @Override
    public void deleteById(Long id) {
        this.findById(id);
        this.repository.deleteById(id);
    }

    @Override
    public void delete(Taille taille) {
        this.repository.delete(taille);
    }
}
