package com.msr.cg.afrimeta.modepaiement;

import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class ModePaiementServiceTest {
    @InjectMocks
    private ModePaiementService service;

    @Mock
    private ModePaiementRepository repository;

    List<ModePaiement> modePaiementList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        ModePaiement modePaiement1 = new ModePaiement();
        modePaiement1.setNom("Aitel Money");

        ModePaiement modePaiement2 = new ModePaiement();
        modePaiement2.setNom("MTN Money");

        ModePaiement modePaiement3 = new ModePaiement();
        modePaiement3.setNom("Western Money");

        this.modePaiementList.add(modePaiement1);
        this.modePaiementList.add(modePaiement2);
        this.modePaiementList.add(modePaiement3);

    }

    @Test
    void findAll() {
        //Given
        given(repository.findAll()).willReturn(modePaiementList);
        //When
        List<ModePaiement> modePaiements = service.findAll();
        //Then
        assertThat(modePaiements).isEqualTo(modePaiementList);
        assertThat(modePaiements.size()).isEqualTo(3);
        assertThat(modePaiements.get(0).getNom()).isEqualTo("Aitel Money");
        verify(this.repository,times(1)).findAll();
    }

    @Test
    void findByIdSuccess() {
        //Given
        given(this.repository.findById(1L)).willReturn(Optional.of(this.modePaiementList.get(0)));
        //When
        ModePaiement modePaiement = service.findById(1L);
        //then
        assertThat(modePaiement).isEqualTo(this.modePaiementList.get(0));
        assertThat(modePaiement.getNom()).isEqualTo("Aitel Money");
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
        assertThat(throwable).hasMessage("Nous ne retrouvons pas l'entité modePaiement avec id 12");
        verify(this.repository,times(1)).findById(12L);
    }

    @Test
    void save() {
        //Given
        given(this.repository.save(Mockito.any(ModePaiement.class))).willReturn(this.modePaiementList.get(0));
        //When
        ModePaiement modePaiement = service.save(new ModePaiement());
        //Then
        assertThat(modePaiement).isEqualTo(this.modePaiementList.get(0));
        assertThat(modePaiement.getNom()).isEqualTo("Aitel Money");
        verify(this.repository,times(1)).save(Mockito.any(ModePaiement.class));
    }

    @Test
    void updateByIdSuccess() {
        ModePaiement modePaiement1 = new ModePaiement();
        modePaiement1.setModePaiementId(1L);
        modePaiement1.setNom("Aitel Money à jours");
        //Given
        given(this.repository.findById(1L)).willReturn(Optional.of(modePaiement1));
        given(this.repository.save(modePaiement1)).willReturn(modePaiement1);

        //When
        ModePaiement modePaiement = this.service.update(modePaiement1,1L);
        //Then
        assertThat(modePaiement).isEqualTo(modePaiement1);
        assertThat(modePaiement.getNom()).isEqualTo("Aitel Money à jours");
        verify(this.repository,times(1)).findById(1L);
        verify(this.repository,times(1)).save(modePaiement1);
    }

    @Test
    void updateByIdNotFound() {
        ModePaiement modePaiement1 = new ModePaiement();
        modePaiement1.setModePaiementId(1L);
        modePaiement1.setNom("Aitel Money à jours");
        //Given
        given(this.repository.findById(11L)).willReturn(Optional.empty());

        //When
        Throwable throwable = catchThrowable(() -> service.update(modePaiement1,11L));
        //then
        assertThat(throwable).isInstanceOf(ObjectNotFoundException.class);
        assertThat(throwable).hasMessage("Nous ne retrouvons pas l'entité modePaiement avec id 11");
        verify(this.repository,times(1)).findById(11L);
    }

    @Test
    void deleteById() {
        //Given
        given(this.repository.findById(1L)).willReturn(Optional.of(this.modePaiementList.get(0)));
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
        assertThat(throwable).hasMessage("Nous ne retrouvons pas l'entité modePaiement avec id 10");
        verify(this.repository,times(1)).findById(10L);
    }
}