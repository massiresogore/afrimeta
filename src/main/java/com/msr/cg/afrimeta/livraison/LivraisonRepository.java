package com.msr.cg.afrimeta.livraison;

import com.msr.cg.afrimeta.paiement.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivraisonRepository extends JpaRepository<Livraison, Long> {
}
