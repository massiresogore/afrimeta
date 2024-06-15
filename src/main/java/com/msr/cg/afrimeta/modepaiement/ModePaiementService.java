package com.msr.cg.afrimeta.modepaiement;

import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import com.msr.cg.afrimeta.utils.AfrimetaCrudInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModePaiementService implements AfrimetaCrudInterface<ModePaiement> {
    private final ModePaiementRepository repository;

    public ModePaiementService(ModePaiementRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ModePaiement> findAll() {
        return this.repository.findAll();
    }

    @Override
    public ModePaiement findById(Long modePaiementId) {
        return this.repository.findById(modePaiementId).orElseThrow(() -> new ObjectNotFoundException("modePaiement",modePaiementId));
    }

    @Override
    public ModePaiement save(ModePaiement modePaiement) {
        return this.repository.save(modePaiement);
    }

    @Override
    public ModePaiement update(ModePaiement modePaiement, Long modePaiementId) {
        return this.repository.findById(modePaiementId)
                .map(oldModePaiement->{
                    oldModePaiement.setNom(modePaiement.getNom());
                    return this.repository.save(oldModePaiement);
                })
                .orElseThrow(() -> new ObjectNotFoundException("modePaiement",modePaiementId));
    }

    @Override
    public void deleteById(Long oldModePaiement) {
        this.findById(oldModePaiement);
        this.repository.deleteById(oldModePaiement);
    }

    @Override
    public void delete(ModePaiement modePaiement) {
        this.repository.delete(modePaiement);
    }
}
