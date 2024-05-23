package com.msr.cg.afrimeta.couleur;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface CouleurRepository extends JpaRepository<Couleur, Long> {
}
