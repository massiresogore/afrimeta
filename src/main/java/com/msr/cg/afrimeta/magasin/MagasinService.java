package com.msr.cg.afrimeta.magasin;

import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import com.msr.cg.afrimeta.utils.AfrimetaCrudInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MagasinService implements AfrimetaCrudInterface<Magasin> {
    private final MagasinRepository magasinRepository;

    public MagasinService(MagasinRepository magasinRepository) {
        this.magasinRepository = magasinRepository;
    }

    @Override
    public List<Magasin> findAll() {
        return this.magasinRepository.findAll();
    }

    @Override
    public Magasin findById(Long id) {
        return this.magasinRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("magasin",id));
    }

    @Override
    public Magasin save(Magasin magasin) {
        return this.magasinRepository.save(magasin);
    }

    @Override
    public Magasin update(Magasin magasin, Long id) {
      return   this.magasinRepository.findById(id)
                .map(oldMagasin->{
                    oldMagasin.setLibele(magasin.getLibele());
                    oldMagasin.setLogoUrl(magasin.getLogoUrl());
                    oldMagasin.setDescription(magasin.getDescription());
                  return this.magasinRepository.save(oldMagasin);
                })
                .orElseThrow(() -> new ObjectNotFoundException("magasin",id));
    }

    @Override
    public void deleteById(Long id) {
        this.findById(id);
        this.magasinRepository.deleteById(id);
    }

    @Override
    public void delete(Magasin magasin) {
        this.findById(magasin.magasinId);
        this.magasinRepository.delete(magasin);
    }
}
