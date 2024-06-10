package com.msr.cg.afrimeta.facture;

import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import  static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class FactureServiceTest {

    @Mock
    FactureRepository repository;

    @InjectMocks
    FactureService factureService;

    List<Facture> factures = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Facture facture1 = new Facture(1L,LocalDate.now(),22,22,2);
        Facture facture2 = new Facture(1L,LocalDate.now(),23,21,3);
        Facture facture3 = new Facture(1L,LocalDate.now(),24,11,1);
        Facture facture4 = new Facture(1L,LocalDate.now(),25,99,3);

        factures.add(facture1);
        factures.add(facture2);
        factures.add(facture3);
        factures.add(facture4);

    }

    @Test
    void findAll() {
        //Given
        given(repository.findAll()).willReturn(factures);
        //When
        List<Facture> actual = factureService.findAll();
        //Then
        assertThat(actual).isEqualTo(factures);
        verify(this.repository,times(1)).findAll();
    }

    @Test
    void findById() {
        //Given
        given(this.repository.findById(1L)).willReturn(Optional.of(this.factures.get(0)));
        //When
        Facture actual = factureService.findById(1L);
        //Then
        assertThat(actual).isEqualTo(factures.get(0));
        verify(this.repository, times(1)).findById(1L);
    }

    @Test
    void findByIdNotFound() {
        //Given
        given(this.repository.findById(1L)).willReturn(Optional.empty());
        //When
        Throwable throwable = assertThrows(Throwable.class, () -> this.factureService.findById(1L));
        //Then
        assertThat(throwable)
                .hasMessage("Nous ne retrouvons pas l'entité facture avec id 1")
                .isInstanceOf(ObjectNotFoundException.class);
        verify(this.repository, times(1)).findById(1L);
    }

    @Test
    void save() {
        //Given
        given(this.repository.save(this.factures.get(0))).willReturn(this.factures.get(0));
        //When
        Facture actual = factureService.save(this.factures.get(0));
        //Then
        assertThat(actual).isEqualTo(factures.get(0));
    }

    @Test
    void update() {
        Facture facture1 = new Facture(1L,LocalDate.now(),22,22,2);
        //Given
        given(this.repository.findById(1L)).willReturn(Optional.of(facture1));
        given(this.repository.save(facture1)).willReturn(facture1);
        //When
        Facture updateFacture = this.factureService.update(facture1,1L);
        //Then
        assertThat(updateFacture).isEqualTo(facture1);
        verify(this.repository, times(1)).save(facture1);
    }
    @Test
    void updateByIdNotFound() {
        Facture facture1 = new Facture(1L,LocalDate.now(),22,22,2);
        //Given
        given(this.repository.findById(1L)).willReturn(Optional.empty());

        Throwable throwable = assertThrows(Throwable.class, () -> this.factureService.update(facture1,1L));

        //When
        assertThat(throwable)
                .isInstanceOf(ObjectNotFoundException.class)
                        .hasMessage("Nous ne retrouvons pas l'entité facture avec id 1");
        //Then
        verify(this.repository, times(1)).findById(1L);
    }

    @Test
    void deleteById() {
        given(this.repository.findById(1L)).willReturn(Optional.of(this.factures.get(0)));
        doNothing().when(this.repository).deleteById(1L);
        this.factureService.deleteById(1L);
        verify(this.repository, times(1)).findById(1L);
        verify(this.repository, times(1)).deleteById(1L);
    }

    @Test
    void deleteByIdNot() {
        given(this.repository.findById(1L)).willReturn(Optional.empty());

        Throwable throwable = assertThrows(Throwable.class, () -> this.factureService.deleteById(1L));
        assertThat(throwable)
        .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Nous ne retrouvons pas l'entité facture avec id 1");

        verify(this.repository, times(1)).findById(1L);
    }
}