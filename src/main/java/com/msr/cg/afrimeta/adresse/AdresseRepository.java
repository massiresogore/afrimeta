package com.msr.cg.afrimeta.adresse;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("afrimetaJdbcClient")
public interface AdresseRepository extends JpaRepository<Adresse, Long> {
}
