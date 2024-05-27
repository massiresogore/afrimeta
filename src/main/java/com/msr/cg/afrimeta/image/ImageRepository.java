package com.msr.cg.afrimeta.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query("select i from Image i  where i.produit.produitId=:produitId")
    List<Image> selectAllImageByProduitId(Long produitId);

    List<Image> findByName(String name);
}
