package com.msr.cg.afrimeta.produit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {

    @Query("select p from Produit p JOIN FETCH p.categorie JOIN FETCH p.typeProduit JOIN FETCH p.images JOIN FETCH p.website where p.website.websiteId=:theId")
    List<Produit> selectProduitWithCategorieAndTypeProduitAndImagesByWebsiteId(Long theId);

    /********** Produit par websiteId ********/
    @Query("select p from Produit p  where p.website.websiteId=:theId")
    List<Produit> selectProduitByWebsiteId(Long theId);

    @Query("select p from Produit p JOIN FETCH p.categorie JOIN FETCH p.typeProduit JOIN FETCH p.images where p.produitId=:theId")
    Produit singleProduitWithCategorieAndTypeProduitByProduitId(int theId);

    @Query(value = "select p from Produit p JOIN FETCH p.categorie JOIN FETCH p.typeProduit JOIN FETCH p.images JOIN FETCH p.website")
    Page<Produit> findAllProduitWithPagination(Pageable pageable);

  /*  @Query(value = "select * from produit join website on produit.website_id = website.website_id join magasin on website.magasin_id = magasin.magasin_id, where magasin.magasin_id = ?",nativeQuery = true)
    List<Produit> findAllProduitsByIdMagasin(Long idMagasin);*/


}

/*
* categorie_id
type_produit_id
website_id

* */