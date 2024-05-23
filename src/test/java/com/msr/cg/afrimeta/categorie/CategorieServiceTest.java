package com.msr.cg.afrimeta.categorie;

import com.msr.cg.afrimeta.categorie.dto.CategorieDto;
import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategorieServiceTest {
    @InjectMocks
    private CategorieService service;
    @Mock
    private CategorieRepository repository;

    List<Categorie> categories = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Categorie c1 = new Categorie(1L,"Dior");
        Categorie c2 = new Categorie(1L,"Nike");
        Categorie c3 = new Categorie(1L,"Adidas");
        Categorie c4 = new Categorie(1L,"Hermes");
        Categorie c5 = new Categorie(1L,"Mosh");
        categories.add(c1);
        categories.add(c2);
        categories.add(c3);
        categories.add(c4);
        categories.add(c5);
    }

    @Test
    void findAll() {
        //given
        given(repository.findAll()).willReturn(categories);
        //When
        List<Categorie> actualCategories = service.findAll();
        //Then
        assertThat(actualCategories).isEqualTo(categories);
        assertThat(actualCategories.size()).isEqualTo(5);
        verify(this.repository,times(1)).findAll();
    }

    @Test
    void findById() {
        //Given
        given(this.repository.findById(1L)).willReturn(Optional.of(this.categories.get(0)));
        //When
        Categorie actualCategory = service.findById(1L);
        //Then
        assertThat(actualCategory).isEqualTo(this.categories.get(0));
        assertThat(actualCategory.getNom()).isEqualTo("Dior");
        verify(this.repository, times(1)).findById(1L);
    }  @Test
    void findByIdNotFound() {
        //Given
        given(this.repository.findById(1L)).willReturn(Optional.empty());
        //When
        Throwable throwable = catchThrowable(()->service.findById(1L));
        //Then
        assertThat(throwable)
                .hasMessage("Nous ne retrouvons pas l'entité categorie avec id 1")
                .isInstanceOf(ObjectNotFoundException.class);
        verify(this.repository, times(1)).findById(1L);
    }

    @Test
    void save() {
        //Dto
        CategorieDto categorieDto = new CategorieDto(null,"Dior");
        //Object
        Categorie categorie = new Categorie(categorieDto.nom());
        //GIven
        given(this.repository.save(categorie)).willReturn(categorie);
        //When
        Categorie actualCategory = service.save(categorie);
        //Then
        assertThat(actualCategory).isEqualTo(categorie);
        assertThat(actualCategory.getNom()).isEqualTo("Dior");
        verify(this.repository, times(1)).save(categorie);
    }

    @Test
    void updateByIdSuccess() {
        //Dto
        CategorieDto categorieDto = new CategorieDto(1L,"Dior update");
        //Object
        Categorie categorie = new Categorie(categorieDto.categorieId(),categorieDto.nom());
        //GIven
        given(this.repository.findById(1L)).willReturn(Optional.of(categorie));
        given(this.repository.save(categorie)).willReturn(categorie);
        //When
        Categorie savedCategorie = service.update(categorie,1L);
        //Then
        assertThat(savedCategorie).isEqualTo(categorie);
        assertThat(savedCategorie.getNom()).isEqualTo("Dior update");
        verify(this.repository, times(1)).findById(1L);
        verify(this.repository, times(1)).save(categorie);
    }

    @Test
    void updateByIdNotFound() {
        //Dto
        CategorieDto categorieDto = new CategorieDto(1L,"Dior update");
        //Object
        Categorie categorie = new Categorie(categorieDto.categorieId(),categorieDto.nom());
        //GIven
        given(this.repository.findById(1L)).willReturn(Optional.empty());
        //When
        Throwable throwable = catchThrowable(()->service.update(categorie,1L));
        //Then
        assertThat(throwable)
                .hasMessage("Nous ne retrouvons pas l'entité categorie avec id 1")
                .isInstanceOf(ObjectNotFoundException.class);
        verify(this.repository, times(1)).findById(1L);
    }

    @Test
    void deleteById() {
        //Given
        given(this.repository.findById(1L)).willReturn(Optional.of(this.categories.get(0)));
        doNothing().when(this.repository).deleteById(1L);
        //When
        this.service.deleteById(1L);
        verify(this.repository, times(1)).findById(1L);
    }

    @Test
    void deleteByIdNotFound() {
        //Given
        given(this.repository.findById(1L)).willReturn(Optional.empty());
        //When
        Throwable throwable = catchThrowable(()->service.deleteById(1L));
        assertThat(throwable)
                .hasMessage("Nous ne retrouvons pas l'entité categorie avec id 1")
        .isInstanceOf(ObjectNotFoundException.class);
        verify(this.repository, times(1)).findById(1L);
    }
}