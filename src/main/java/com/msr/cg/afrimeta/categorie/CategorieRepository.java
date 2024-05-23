package com.msr.cg.afrimeta.categorie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface CategorieRepository extends JpaRepository<Categorie, Long> {
}
