package com.msr.cg.afrimeta.commentaire;

import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import com.msr.cg.afrimeta.utils.AfrimetaCrudInterface;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentaireService implements AfrimetaCrudInterface<Commentaire> {
    private final CommentaireRepository repository;

    public CommentaireService(CommentaireRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Commentaire> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Commentaire findById(Long id) {
        return this.repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(Commentaire.class.getSimpleName(),id));
    }

    @Override
    public Commentaire save(Commentaire commentaire) {
        return this.repository.save(commentaire);
    }

    @Override
    public Commentaire update(Commentaire commentaire, Long id) {

        return this.repository.findById(id)
                .map(oldCommentaire->{
                    oldCommentaire.setCommentaireDate(commentaire.getCommentaireDate());
                    oldCommentaire.setCommentaireDate(commentaire.getCommentaireDate()==null ? LocalDateTime.now():commentaire.getCommentaireDate());
                    oldCommentaire.setContenu(commentaire.getContenu());
                    oldCommentaire.setProduit(commentaire.getProduit());
                    oldCommentaire.setClientUser(commentaire.getClientUser());
                    System.out.println(oldCommentaire);
                    return this.repository.save(oldCommentaire);
                })
                .orElseThrow(() -> new ObjectNotFoundException(Commentaire.class.getSimpleName(),id));
    }

    @Override
    public void deleteById(Long id) {
        this.findById(id);
        this.repository.deleteById(id);

    }

    @Override
    public void delete(Commentaire commentaire) {
        this.repository.delete(commentaire);
    }
}
