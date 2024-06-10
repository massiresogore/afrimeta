package com.msr.cg.afrimeta.facture;

import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import com.msr.cg.afrimeta.utils.AfrimetaCrudInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FactureService implements AfrimetaCrudInterface<Facture> {
    private final FactureRepository factureRepository;

    public FactureService(FactureRepository factureRepository) {
        this.factureRepository = factureRepository;
    }

    @Override
    public List<Facture> findAll() {
        return this.factureRepository.findAll();
    }

    @Override
    public Facture findById(Long id) {
        return this.factureRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException("facture", id));
    }

    @Override
    public Facture save(Facture facture) {
        return this.factureRepository.save(facture);
    }

    @Override
    public Facture update(Facture facture, Long id) {
      return  this.factureRepository.findById(id)
                 .map(oldFacture -> {
                     oldFacture.setTotalHorsTaxe(facture.getTotalHorsTaxe());
                     oldFacture.setTotalToutTaxeComprise(facture.getTotalToutTaxeComprise());
                     oldFacture.setTotalTva(facture.getTotalTva());
                     return this.factureRepository.save(oldFacture);
                 })
                 .orElseThrow(()-> new ObjectNotFoundException("facture", id));
    }

    @Override
    public void deleteById(Long id) {
        this.findById(id);
        this.factureRepository.deleteById(id);
    }

    @Override
    public void delete(Facture facture) {

        this.factureRepository.delete(facture);
    }
}
