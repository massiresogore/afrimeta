package com.msr.cg.afrimeta.embaddable;

import com.msr.cg.afrimeta.produit.Produit;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContenirRepository extends JpaRepository<Contenir, Long> {

   // List<Produit> findAllByCommande_CommandeId(Long commandeId);
}
