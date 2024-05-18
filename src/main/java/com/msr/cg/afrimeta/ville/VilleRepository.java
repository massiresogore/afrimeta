package com.msr.cg.afrimeta.ville;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("afrimetaJdbcClient")
public interface VilleRepository extends JpaRepository<Ville, Long> {
}
