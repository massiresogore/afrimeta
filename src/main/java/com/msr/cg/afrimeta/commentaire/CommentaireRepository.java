package com.msr.cg.afrimeta.commentaire;

import com.msr.cg.afrimeta.livraison.Livraison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {
}
