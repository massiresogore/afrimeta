package com.msr.cg.afrimeta.adresse;

import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdresseService {

   private final AdresseRepository adresseRepository;

    public AdresseService(AdresseRepository adresseRepository) {
        this.adresseRepository = adresseRepository;
    }

    public List<Adresse> findAll() {
        return adresseRepository.findAll();
    }
    public Adresse findById(Long id) {
        return adresseRepository.findById(id).orElseThrow(()->new ObjectNotFoundException("Adresse with id " + id + " not found", id));
    }
    public Adresse save(Adresse adresse) {
        return adresseRepository.save(adresse);
    }
    public Adresse update(Adresse adresse, Long id) {
      return   this.adresseRepository.findById(id).map(oldAdresse ->{
            oldAdresse.setCp(adresse.getCp());
            oldAdresse.setNumero(adresse.getNumero());
            oldAdresse.setVille(adresse.getVille());
            return adresseRepository.save(oldAdresse);
        }).orElseThrow(()->new ObjectNotFoundException("Adresse", id));
    }

    public void deleteById(Long id) {
        this.findById(id);
        this.adresseRepository.deleteById(id);
    }
}
