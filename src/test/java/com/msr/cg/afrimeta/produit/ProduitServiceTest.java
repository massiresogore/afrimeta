package com.msr.cg.afrimeta.produit;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;

import com.msr.cg.afrimeta.categorie.Categorie;
import com.msr.cg.afrimeta.couleur.Couleur;
import com.msr.cg.afrimeta.image.Image;
import com.msr.cg.afrimeta.produit.dto.dto.ProduitDto;
import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import com.msr.cg.afrimeta.taille.Taille;
import com.msr.cg.afrimeta.typeproduit.TypeProduit;
import com.msr.cg.afrimeta.website.Website;
import com.msr.cg.afrimeta.website.WebsiteRepository;
import com.msr.cg.afrimeta.website.WebsiteService;
import com.msr.cg.afrimeta.website.dto.SingleWebsiteResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProduitServiceTest {

    @InjectMocks
    ProduitService service;

    @InjectMocks
    WebsiteService websiteService;

    @Mock
    ProduitRepository repository;

    @Mock
    WebsiteRepository websiteRepository;



    List<Produit> produits = new ArrayList<>();

    @BeforeEach
    void setUp() {

        Website website1 = new Website(1L,"http://google.com",null);
        Website website2 = new Website(2L,"http://google.com",null);
        Website website3 = new Website(3L,"http://google.com",null);
        Website website4 = new Website(4L,"http://google.com",null);
        Website website5 = new Website(5L,"http://google.com",null);

        Produit produit1 = new Produit(null, "Apple iPhone 14", "Latest model with advanced features", 50, 999.99, null, null, null, website1);
        Produit produit2 = new Produit(null, "Samsung Galaxy S21", "High performance and sleek design", 30,  799.99, null, null, null, website2);
        Produit produit3 = new Produit(null, "Sony WH-1000XM4", "Noise cancelling wireless headphones", 100, 349.99, null, null, null, website3);
        Produit produit4 = new Produit(null, "Dell XPS 13", "Compact and powerful laptop", 20,  1299.99, null, null, null, website4);
        Produit produit5 = new Produit(null, "Nintendo Switch", "Versatile gaming console", 70,  299.99, null, null, null, website5);

        produits.add(produit1);
        produits.add(produit2);
        produits.add(produit3);
        produits.add(produit4);
        produits.add(produit5);
    }

    @Test
    void findAll() {
        //given
        given(repository.findAll()).willReturn(produits);
        //when
        List<Produit> produits = service.findAll();
        //Then
        assertThat(produits).hasSize(5);
        assertThat(this.produits.get(0)).isEqualTo(produits.get(0));
        assertThat(this.produits.get(1)).isEqualTo(produits.get(1));
        verify(this.repository,times(1)).findAll();
    }

    @Test
    void findById() {
        //given
        given(this.repository.findById(1L)).willReturn(Optional.of(this.produits.get(0)));
        //When
        Produit produit = service.findById(1L);
        //Then
        assertThat(produit).isEqualTo(this.produits.get(0));
        assertThat(this.produits.get(0).getTypeProduit()).isEqualTo(produit.getTypeProduit());
        assertThat(this.produits.get(0).getDescription()).isEqualTo(produit.getDescription());
        verify(this.repository,times(1)).findById(1L);
    }

    @Test
    void findByIdNotFound() {
        //given
        given(this.repository.findById(1L)).willReturn(Optional.empty());
        //when
        Throwable throwable = assertThrows(ObjectNotFoundException.class, () -> service.findById(1L));
        //then
        assertThat(throwable.getMessage()).isEqualTo("Nous ne retrouvons pas l'entité Produit avec id 1");
        verify(this.repository,times(1)).findById(1L);
    }

    @Test
    void save() {
        //Catégorie
        Categorie defaultCategorie = new Categorie("default categorie");

        //Type produit and taille
        TypeProduit defaultTypeProduit = new TypeProduit("default type produit");
        Taille defaultTaille = new Taille("default taille");
        defaultTypeProduit.addTaille(defaultTaille);

        //Couleur
        Couleur defaultCouleur = new Couleur("default couleur");
        List<Couleur> couleurs = new ArrayList<>();
        couleurs.add(defaultCouleur);

        //Image
        Image image = new Image("image/jpeg","http://localhost:8080/produit.jpeg","ordinateur");
        List<Image> images = new ArrayList<>();
        images.add(image);

        List<String> imagePaths = new ArrayList<>();
        images.stream().peek(image1 -> imagePaths.add(image1.getName()));

        // dto
        Website website1 = new Website(1L,"http://google.com",null);
        SingleWebsiteResponse websiteResponse = new SingleWebsiteResponse(website1.getWebsiteId(),website1.getWebsiteUrl());
        ProduitDto produitDto = new ProduitDto(null, "Apple iPhone 14", "Latest model with advanced features", 50,  999.99, LocalDate.now(), defaultCategorie, defaultTypeProduit, website1,couleurs,imagePaths,websiteResponse);

        /*********/
        //object
        Produit produit = new Produit();
        produit.setTitre(produitDto.titre());
        produit.setDescription(produitDto.description());
        produit.setQuantiteStock(produitDto.quantiteStock());
        produit.setPrix(produitDto.prix());
        produit.setCategorie(defaultCategorie);
        produit.setTypeProduit(defaultTypeProduit);
        produit.addCouleur(defaultCouleur);
        produit.setImages(images);
        produit.setWebsite(website1);

        /*********/
        //given
        given(this.websiteRepository.findById(1L)).willReturn(Optional.of(website1));
        given(this.repository.save(Mockito.any(Produit.class))).willReturn(produit);
        //when

        Produit savedProduit = service.save(produit);
        Website websiteFound =this.websiteService.findById(1L);

        //then
        assertThat(websiteFound).isEqualTo(website1);
        assertThat(savedProduit.getQuantiteStock()).isEqualTo(produit.getQuantiteStock());
        assertThat(savedProduit.getPrix()).isEqualTo(produit.getPrix());
        assertThat(savedProduit.getTypeProduit()).isEqualTo(produit.getTypeProduit());
        assertThat(savedProduit.getDescription()).isEqualTo(produit.getDescription());
        assertThat(savedProduit.getPrix()).isEqualTo(produit.getPrix());
        assertThat(savedProduit.getCategorie()).isEqualTo(produit.getCategorie());

        verify(this.repository,times(1)).save(produit);
        verify(this.websiteRepository,times(1)).findById(1L);
    }

    @Test
    void updateById() {
        // dto
        Website website1 = new Website(1L,"http://google.com",null);
        ProduitDto produitDto = new ProduitDto(1L, "Apple iPhone 14 updated", "Latest model with advanced features", 50,  999.99, null, null, null, website1,null,null,null);

        //object
        Produit produitToSave = new Produit(
                produitDto.produitId(),
                produitDto.titre(),
                produitDto.description(),
                produitDto.quantiteStock(),
                produitDto.prix(),
                produitDto.dateAjout(),
                produitDto.categorie(),
                produitDto.typeProduit(),
                produitDto.website()
        );
        //given
        given(this.repository.findById(1L)).willReturn(Optional.of(produitToSave));
        given(this.repository.save(produitToSave)).willReturn(produitToSave);
        //when
        Produit savedProduit = service.update(produitToSave,1L);
        //then
        assertThat(savedProduit).isEqualTo(produitToSave);
        assertThat(savedProduit.getTypeProduit()).isEqualTo(produitToSave.getTypeProduit());
        assertThat(savedProduit.getDescription()).isEqualTo(produitToSave.getDescription());
        assertThat(savedProduit.getPrix()).isEqualTo(produitToSave.getPrix());
        assertThat(savedProduit.getCategorie()).isEqualTo(produitToSave.getCategorie());
        verify(this.repository,times(1)).findById(1L);
        verify(this.repository,times(1)).save(produitToSave);

    }

    @Test
    void updateByIdNotFound() {
        // dto
        Website website1 = new Website(1L,"http://google.com",null);
        ProduitDto produitDto = new ProduitDto(1L, "Apple iPhone 14 updated", "Latest model with advanced features", 50, 999.99, null, null, null, website1,null,null,null);

        //object
        Produit produitToSave = new Produit(
                produitDto.produitId(),
                produitDto.titre(),
                produitDto.description(),
                produitDto.quantiteStock(),
                produitDto.prix(),
                produitDto.dateAjout(),
                produitDto.categorie(),
                produitDto.typeProduit(),
                produitDto.website()
        );
        //given
        given(this.repository.findById(1L)).willReturn(Optional.empty());
        //when
       Throwable throwable =  assertThrows(ObjectNotFoundException.class, () -> service.update(produitToSave,1L));
        //then
        assertThat(throwable.getMessage()).isEqualTo("Nous ne retrouvons pas l'entité Produit avec id 1");
        verify(this.repository,times(1)).findById(1L);
    }

    @Test
    void deleteById() {
        //given
        given(this.repository.findById(1L)).willReturn(Optional.of(this.produits.get(0)));
        doNothing().when(this.repository).deleteById(1L);
        //When
        this.service.deleteById(1L);
        verify(this.repository,times(1)).deleteById(1L);
    }

    @Test
    void deleteByIdNotFound() {
        //given
        given(this.repository.findById(1L)).willReturn(Optional.empty());
        Throwable throwable =  catchThrowable( () -> this.service.deleteById(1L));
        assertThat(throwable)
                .hasMessage("Nous ne retrouvons pas l'entité Produit avec id 1")
                .isInstanceOf(ObjectNotFoundException.class);
        verify(this.repository,times(1)).findById(1L);
    }
}