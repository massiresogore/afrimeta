package com.msr.cg.afrimeta.embaddable;

import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContenirRepository extends JpaRepository<Contenir, Long> {
}
