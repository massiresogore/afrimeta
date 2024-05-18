package com.msr.cg.afrimeta.ville;

import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VilleService {

    private final VilleRepository villeRepository;

    public VilleService(VilleRepository villeRepository) {
        this.villeRepository = villeRepository;
    }

    public Ville save(Ville ville) {
        return this.villeRepository.save(ville);
    }

    public Ville findById(int id) {
        Long theId = (long) id;
        return this.villeRepository.findById(theId).orElseThrow(()->new ObjectNotFoundException("Ville", theId));
    }
    public List<Ville> findAll() {
        return villeRepository.findAll();
    }


    public void deleteById(Long id) {
        this.findById(Math.toIntExact(id));
        this.villeRepository.deleteById(id);
    }

    public Ville update(Ville ville, Long id) {
        return this.villeRepository.findById(id)
                .map(oldVille -> {
                    oldVille.setNom(ville.getNom());
                    return this.villeRepository.save(oldVille);
                })
                .orElseThrow(()->new ObjectNotFoundException("Ville", id));

    }
}
