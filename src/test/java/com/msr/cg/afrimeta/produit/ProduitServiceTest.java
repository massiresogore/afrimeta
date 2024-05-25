package com.msr.cg.afrimeta.produit;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import com.msr.cg.afrimeta.produit.dto.ProduitDto;
import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import com.msr.cg.afrimeta.website.Website;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProduitServiceTest {

    @InjectMocks
    ProduitService service;

    @Mock
    ProduitRepository repository;

    List<Produit> produits = new ArrayList<>();

    @BeforeEach
    void setUp() {

        Website website1 = new Website(1L,"http://google.com",null);
        Website website2 = new Website(2L,"http://google.com",null);
        Website website3 = new Website(3L,"http://google.com",null);
        Website website4 = new Website(4L,"http://google.com",null);
        Website website5 = new Website(5L,"http://google.com",null);

        Produit produit1 = new Produit(null, "Apple iPhone 14", "Latest model with advanced features", 50, "http://example.com/image1.jpg", 999.99, null, null, null, website1);
        Produit produit2 = new Produit(null, "Samsung Galaxy S21", "High performance and sleek design", 30, "http://example.com/image2.jpg", 799.99, null, null, null, website2);
        Produit produit3 = new Produit(null, "Sony WH-1000XM4", "Noise cancelling wireless headphones", 100, "http://example.com/image3.jpg", 349.99, null, null, null, website3);
        Produit produit4 = new Produit(null, "Dell XPS 13", "Compact and powerful laptop", 20, "http://example.com/image4.jpg", 1299.99, null, null, null, website4);
        Produit produit5 = new Produit(null, "Nintendo Switch", "Versatile gaming console", 70, "http://example.com/image5.jpg", 299.99, null, null, null, website5);

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
        // dto
        Website website1 = new Website(1L,"http://google.com",null);
        ProduitDto produitDto = new ProduitDto(null, "Apple iPhone 14", "Latest model with advanced features", 50, "http://example.com/image1.jpg", 999.99, null, null, null, website1);

        //object
        Produit produitToSave = new Produit(
                produitDto.produitId(),
                produitDto.titre(),
                produitDto.description(),
                produitDto.quantiteStock(),
                produitDto.imageUrl(),
                produitDto.prix(),
                produitDto.dateAjout(),
                produitDto.categorie(),
                produitDto.typeProduit(),
                produitDto.website()
        );
        //given
        given(this.repository.save(produitToSave)).willReturn(produitToSave);
        //when
        Produit savedProduit = service.save(produitToSave);
        //then
        assertThat(savedProduit).isEqualTo(produitToSave);
        assertThat(savedProduit.getTypeProduit()).isEqualTo(produitToSave.getTypeProduit());
        assertThat(savedProduit.getDescription()).isEqualTo(produitToSave.getDescription());
        assertThat(savedProduit.getPrix()).isEqualTo(produitToSave.getPrix());
        assertThat(savedProduit.getImageUrl()).isEqualTo(produitToSave.getImageUrl());
        assertThat(savedProduit.getCategorie()).isEqualTo(produitToSave.getCategorie());
        verify(this.repository,times(1)).save(produitToSave);
    }

    @Test
    void updateById() {
        // dto
        Website website1 = new Website(1L,"http://google.com",null);
        ProduitDto produitDto = new ProduitDto(1L, "Apple iPhone 14 updated", "Latest model with advanced features", 50, "http://example.com/image1.jpg", 999.99, null, null, null, website1);

        //object
        Produit produitToSave = new Produit(
                produitDto.produitId(),
                produitDto.titre(),
                produitDto.description(),
                produitDto.quantiteStock(),
                produitDto.imageUrl(),
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
        assertThat(savedProduit.getImageUrl()).isEqualTo(produitToSave.getImageUrl());
        assertThat(savedProduit.getCategorie()).isEqualTo(produitToSave.getCategorie());
        verify(this.repository,times(1)).findById(1L);
        verify(this.repository,times(1)).save(produitToSave);

    }

    @Test
    void updateByIdNotFound() {
        // dto
        Website website1 = new Website(1L,"http://google.com",null);
        ProduitDto produitDto = new ProduitDto(1L, "Apple iPhone 14 updated", "Latest model with advanced features", 50, "http://example.com/image1.jpg", 999.99, null, null, null, website1);

        //object
        Produit produitToSave = new Produit(
                produitDto.produitId(),
                produitDto.titre(),
                produitDto.description(),
                produitDto.quantiteStock(),
                produitDto.imageUrl(),
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