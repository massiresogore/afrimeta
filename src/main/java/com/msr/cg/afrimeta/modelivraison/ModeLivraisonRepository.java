package com.msr.cg.afrimeta.modelivraison;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ModeLivraisonRepository extends JpaRepository<ModeLivraison, Long> {
}
