package com.msr.cg.afrimeta.modelivraison;

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
class ModeLivraisonServiceTest {
    @InjectMocks
    private ModeLivraisonService service;

    @Mock
    private ModeLivraisonRepository repository;

    List<ModeLivraison> modeLivraisonList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        ModeLivraison modeLivraison1 = new ModeLivraison();
        modeLivraison1.setNom("Aitel Money");

        ModeLivraison modeLivraison2 = new ModeLivraison();
        modeLivraison2.setNom("MTN Money");

        ModeLivraison modeLivraison3 = new ModeLivraison();
        modeLivraison3.setNom("Western Money");

        this.modeLivraisonList.add(modeLivraison1);
        this.modeLivraisonList.add(modeLivraison2);
        this.modeLivraisonList.add(modeLivraison3);

    }

    @Test
    void findAll() {
        //Given
        given(repository.findAll()).willReturn(modeLivraisonList);
        //When
        List<ModeLivraison> modeLivraisons = service.findAll();
        //Then
        assertThat(modeLivraisons).isEqualTo(modeLivraisonList);
        assertThat(modeLivraisons.size()).isEqualTo(3);
        assertThat(modeLivraisons.get(0).getNom()).isEqualTo("Aitel Money");
        verify(this.repository,times(1)).findAll();
    }

    @Test
    void findByIdSuccess() {
        //Given
        given(this.repository.findById(1L)).willReturn(Optional.of(this.modeLivraisonList.get(0)));
        //When
        ModeLivraison modeLivraison = service.findById(1L);
        //then
        assertThat(modeLivraison).isEqualTo(this.modeLivraisonList.get(0));
        assertThat(modeLivraison.getNom()).isEqualTo("Aitel Money");
        verify(this.repository,times(1)).findById(1L);
    }

    @Test
    void findByIdNotFound() {
        //Given
        given(this.repository.findById(12L)).willReturn(Optional.empty());
        //When
        Throwable throwable = catchThrowable(() -> service.findById(12L));
        //then
        assertThat(throwable).isInstanceOf(ObjectNotFoundException.class);
        assertThat(throwable).hasMessage("Nous ne retrouvons pas l'entité modeLivraison avec id 12");
        verify(this.repository,times(1)).findById(12L);
    }

    @Test
    void save() {
        //Given
        given(this.repository.save(Mockito.any(ModeLivraison.class))).willReturn(this.modeLivraisonList.get(0));
        //When
        ModeLivraison modeLivraison = service.save(new ModeLivraison());
        //Then
        assertThat(modeLivraison).isEqualTo(this.modeLivraisonList.get(0));
        assertThat(modeLivraison.getNom()).isEqualTo("Aitel Money");
        verify(this.repository,times(1)).save(Mockito.any(ModeLivraison.class));
    }

    @Test
    void updateByIdSuccess() {
        ModeLivraison modeLivraison1 = new ModeLivraison();
        modeLivraison1.setModeLivraisonId(1L);
        modeLivraison1.setNom("Aitel Money à jours");
        //Given
        given(this.repository.findById(1L)).willReturn(Optional.of(modeLivraison1));
        given(this.repository.save(modeLivraison1)).willReturn(modeLivraison1);

        //When
        ModeLivraison modeLivraison = this.service.update(modeLivraison1,1L);
        //Then
        assertThat(modeLivraison).isEqualTo(modeLivraison1);
        assertThat(modeLivraison.getNom()).isEqualTo("Aitel Money à jours");
        verify(this.repository,times(1)).findById(1L);
        verify(this.repository,times(1)).save(modeLivraison1);
    }

    @Test
    void updateByIdNotFound() {
        ModeLivraison modeLivraison1 = new ModeLivraison();
        modeLivraison1.setModeLivraisonId(1L);
        modeLivraison1.setNom("Aitel Money à jours");
        //Given
        given(this.repository.findById(11L)).willReturn(Optional.empty());

        //When
        Throwable throwable = catchThrowable(() -> service.update(modeLivraison1,11L));
        //then
        assertThat(throwable).isInstanceOf(ObjectNotFoundException.class);
        assertThat(throwable).hasMessage("Nous ne retrouvons pas l'entité modeLivraison avec id 11");
        verify(this.repository,times(1)).findById(11L);
    }

    @Test
    void deleteById() {
        //Given
        given(this.repository.findById(1L)).willReturn(Optional.of(this.modeLivraisonList.get(0)));
        doNothing().when(this.repository).deleteById(1L);

        //When
        this.service.deleteById(1L);
        //Then
        verify(this.repository,times(1)).deleteById(1L);
    }

    @Test
    void deleteByIdNotFound() {
        //Given
        given(this.repository.findById(10L)).willReturn(Optional.empty());


        //When
        Throwable throwable = catchThrowable(() -> this.service.deleteById(10L));
        //then
        assertThat(throwable).isInstanceOf(ObjectNotFoundException.class);
        assertThat(throwable).hasMessage("Nous ne retrouvons pas l'entité modeLivraison avec id 10");
        verify(this.repository,times(1)).findById(10L);
    }
}