package com.msr.cg.afrimeta.taille;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface TailleRepository extends JpaRepository<Taille, Long> {
}
