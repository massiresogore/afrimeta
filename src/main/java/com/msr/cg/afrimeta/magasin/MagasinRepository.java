package com.msr.cg.afrimeta.magasin;

import com.msr.cg.afrimeta.produit.Produit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MagasinRepository extends JpaRepository<Magasin, Long> {
    Page<Magasin> findAll(Pageable pageable);

    @Query(value = "select * from produit join website on produit.website_id = website.website_id join magasin on website.magasin_id = magasin.magasin_id  where magasin.magasin_id=:idMagasin",nativeQuery = true)
    List<Integer> findAllProduitsByIdMagasin(String idMagasin);
}
