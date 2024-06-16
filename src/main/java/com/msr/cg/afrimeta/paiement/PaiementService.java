package com.msr.cg.afrimeta.paiement;

import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import com.msr.cg.afrimeta.utils.AfrimetaCrudInterface;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class PaiementService implements AfrimetaCrudInterface<Paiement> {
    private final PaiementRepository repository;

    public PaiementService(PaiementRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Paiement> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Paiement findById(Long id) {
        return this.repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(Paiement.class.getSimpleName(),id));
    }

    @Override
    public Paiement save(Paiement paiement) {
        return this.repository.save(paiement);
    }

    @Override
    public Paiement update(Paiement paiement, Long id) {

        return this.repository.findById(id)
                .map(oldPaiement->{
                    oldPaiement.setPaiementDate(paiement.getPaiementDate());
                    oldPaiement.setPaiementDate(paiement.getPaiementDate()==null ? LocalDateTime.now():paiement.getPaiementDate());
                    oldPaiement.setDescription(paiement.getDescription());
                    oldPaiement.setModePaiement(paiement.getModePaiement());
                    oldPaiement.setCommande(paiement.getCommande());
                    System.out.println(oldPaiement);
                    return this.repository.save(oldPaiement);
                })
                .orElseThrow(() -> new ObjectNotFoundException(Paiement.class.getSimpleName(),id));
    }

    @Override
    public void deleteById(Long id) {
        this.findById(id);
        this.repository.deleteById(id);

    }

    @Override
    public void delete(Paiement paiement) {
        this.repository.delete(paiement);
    }
}
