package com.msr.cg.afrimeta.modepaiement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ModePaiementRepository extends JpaRepository<ModePaiement, Long> {
}
