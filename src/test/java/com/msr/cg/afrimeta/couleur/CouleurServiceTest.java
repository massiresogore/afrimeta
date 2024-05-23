package com.msr.cg.afrimeta.couleur;

import com.msr.cg.afrimeta.couleur.dto.CouleurDto;
import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.InstanceOfAssertFactories.throwable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CouleurServiceTest {

    @InjectMocks
    CouleurService service;

    @Mock
    CouleurRepository repository;

    List<Couleur> couleurs = new ArrayList<>();
    @BeforeEach
    void setUp() {
        Couleur couleur1 = new Couleur("Yellow");
        Couleur couleur2 = new Couleur("Red");
        Couleur couleur3 = new Couleur("Green");
        Couleur couleur4 = new Couleur("Blue");
        Couleur couleur5 = new Couleur("Yellow");
        couleurs.add(couleur1);
        couleurs.add(couleur2);
        couleurs.add(couleur3);
        couleurs.add(couleur4);
        couleurs.add(couleur5);
    }

    @Test
    void findAll() {
        //Given
        given(repository.findAll()).willReturn(couleurs);
        //When
        List<Couleur> couleurList = service.findAll();
        //then
        assertThat(couleurList).isEqualTo(couleurs);
        assertThat(couleurList.size()).isEqualTo(5);
        verify(this.repository,times(1)).findAll();

    }

    @Test
    void findById() {
        //Given
        given(this.repository.findById(1L)).willReturn(Optional.of(this.couleurs.get(0)));
        //When
        Couleur couleur = service.findById(1L);
        //Then
        assertThat(couleur).isEqualTo(couleurs.get(0));
        assertThat(couleur.getNom()).isEqualTo("Yellow");
        verify(this.repository,times(1)).findById(1L);
    }

    @Test
    void findByIdNotFoubnd() {
        //Given
        given(this.repository.findById(1L)).willReturn(Optional.empty());
        //When
        Throwable throwable = catchThrowable(()->service.findById(1L));
        //Then
        assertThat(throwable)
                .hasMessage("Nous ne retrouvons pas l'entité couleur avec id 1")
                .isInstanceOf(ObjectNotFoundException.class);
        verify(this.repository,times(1)).findById(1L);
    }

    @Test
    void save() {
        //Dto
        CouleurDto couleurDto = new CouleurDto(null,"orange");
        //dto to object
        Couleur couleur = new Couleur(couleurDto.nom());
        //Given
        given(this.repository.save(couleur)).willReturn(couleur);
        //when
        Couleur couleurSaved = service.save(couleur);
        //then
        assertThat(couleurSaved).isEqualTo(couleur);
        assertThat(couleurSaved.getNom()).isEqualTo(couleurDto.nom());
        verify(this.repository,times(1)).save(couleur);
    }

    @Test
    void update() {
        //Dto
        CouleurDto couleurDto = new CouleurDto(1L,"orange");
        //dto to object
        Couleur couleur = new Couleur(couleurDto.couleurId(),couleurDto.nom());
        //Given
        given(this.repository.findById(1L)).willReturn(Optional.of(couleur));
        given(this.repository.save(couleur)).willReturn(couleur);
        //when
        Couleur couleurSaved = service.update(couleur,1L);
        //then
        assertThat(couleurSaved).isEqualTo(couleur);
        assertThat(couleurSaved.getNom()).isEqualTo(couleurDto.nom());
        verify(this.repository,times(1)).save(couleur);
    }

    @Test
    void updateByIdNotFound() {
        //Dto
        CouleurDto couleurDto = new CouleurDto(1L,"orange");
        //dto to object
        Couleur couleur = new Couleur(couleurDto.couleurId(),couleurDto.nom());
        //Given
        given(this.repository.findById(1L)).willReturn(Optional.of(couleur));
        //then
        Throwable throwable = catchThrowable(()->service.update(couleur,1L));
        //Then
        assertThat(throwable)
                .hasMessage("Nous ne retrouvons pas l'entité couleur avec id 1")
                .isInstanceOf(ObjectNotFoundException.class);
        verify(this.repository,times(1)).findById(1L);
    }

    @Test
    void deleteById() {
        //given
        given(this.repository.findById(1L)).willReturn(Optional.of(couleurs.get(0)));
        //When
        doNothing().when(this.repository).deleteById(1L);
        service.deleteById(1L);
        verify(this.repository,times(1)).deleteById(1L);
        //Then
    }

    @Test
    void deleteByIdNotFound() {
        //given
        given(this.repository.findById(1L)).willReturn(Optional.empty());
        //When
        Throwable throwable = catchThrowable(()->service.deleteById(1L));
        //Then
        assertThat(throwable)
                .hasMessage("Nous ne retrouvons pas l'entité couleur avec id 1")
                .isInstanceOf(ObjectNotFoundException.class);
        verify(this.repository,times(1)).findById(1L);
    }
}