package com.msr.cg.afrimeta.magasin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MagasinRepository extends JpaRepository<Magasin, Long> {
    Page<Magasin> findAll(Pageable pageable);
}
