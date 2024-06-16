package com.msr.cg.afrimeta.livraison;

import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import com.msr.cg.afrimeta.utils.AfrimetaCrudInterface;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LivraisonService implements AfrimetaCrudInterface<Livraison> {
    private final LivraisonRepository repository;

    public LivraisonService(LivraisonRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Livraison> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Livraison findById(Long id) {
        return this.repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(Livraison.class.getSimpleName(),id));
    }

    @Override
    public Livraison save(Livraison livraison) {
        return this.repository.save(livraison);
    }

    @Override
    public Livraison update(Livraison livraison, Long id) {

        return this.repository.findById(id)
                .map(oldLivraison->{
                    oldLivraison.setLivraisonDate(livraison.getLivraisonDate());
                    oldLivraison.setLivraisonDate(livraison.getLivraisonDate()==null ? LocalDateTime.now():livraison.getLivraisonDate());
                    oldLivraison.setDescription(livraison.getDescription());
                    oldLivraison.setModeLivraison(livraison.getModeLivraison());
                    oldLivraison.setCommande(livraison.getCommande());
                    System.out.println(oldLivraison);
                    return this.repository.save(oldLivraison);
                })
                .orElseThrow(() -> new ObjectNotFoundException(Livraison.class.getSimpleName(),id));
    }

    @Override
    public void deleteById(Long id) {
        this.findById(id);
        this.repository.deleteById(id);

    }

    @Override
    public void delete(Livraison livraison) {
        this.repository.delete(livraison);
    }
}
