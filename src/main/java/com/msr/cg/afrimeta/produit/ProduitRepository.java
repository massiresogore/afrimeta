package com.msr.cg.afrimeta.produit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {

    @Query("select p from Produit p JOIN FETCH p.categorie JOIN FETCH p.typeProduit where p.website.websiteId=:theId")
    List<Produit> selectProduitWithCategorieAndTypeProduitByWebsiteId(Long theId);

    @Query("select p from Produit p JOIN FETCH p.categorie JOIN FETCH p.typeProduit where p.produitId=:theId")
    Produit singleProduitWithCategorieAndTypeProduitByProduitId(int theId);
}
