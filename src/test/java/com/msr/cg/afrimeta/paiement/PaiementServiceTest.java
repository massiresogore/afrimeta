package com.msr.cg.afrimeta.paiement;

import com.msr.cg.afrimeta.clientUser.ClientUser;
import com.msr.cg.afrimeta.commande.Commande;
import com.msr.cg.afrimeta.modepaiement.ModePaiement;
import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaiementServiceTest {

    @InjectMocks
    PaiementService service;

    @Mock
    PaiementRepository repository;

    List<Paiement> paiements = new ArrayList<>();

    @BeforeEach
    void setUp() {
        //Mode paiement
        ModePaiement modePaiement1 = new ModePaiement();
        modePaiement1.setNom("Aitel Money");
        //ClientUser
        ClientUser clientUser = new ClientUser("emmano","m@gmail.com","MZMZMZMZMZMZZM",true,"ADMIN USER",null);
        Commande commande1 = new Commande(1L, LocalDateTime.now(),22,"33 rue bandas",22,7,clientUser);

        //Maiement
        Paiement paiement1 = new Paiement(1L, LocalDateTime.now(),"description1",modePaiement1,commande1);
        Paiement paiement2 = new Paiement(2L, LocalDateTime.now(),"description2",modePaiement1,commande1);
        Paiement paiement3 = new Paiement(3L, LocalDateTime.now(),"description3",modePaiement1,commande1);
        paiements.add(paiement1);
        paiements.add(paiement2);
        paiements.add(paiement3);
    }

    @Test
    void findAll() {
        //Given
        given(repository.findAll()).willReturn(paiements);
        //When
        List<Paiement> result = service.findAll();

        assertThat(result).isEqualTo(paiements);
        //Then
        verify(this.repository,times(1)).findAll();
    }

    @Test
    void findById() {
        //Given
        given(this.repository.findById(1L)).willReturn(Optional.of(this.paiements.get(0)));
        //When
        Paiement result = service.findById(1L);
        //Then
        assertThat(result).isEqualTo(paiements.get(0));
        verify(this.repository,times(1)).findById(1L);
    }

    @Test
    void findByIdNotFound() {
        //Given
        given(this.repository.findById(1L)).willReturn(Optional.empty());
        //When
        Throwable throwable = assertThrows(ObjectNotFoundException.class, () -> service.findById(1L));
        //Then
        assertThat(throwable).isInstanceOf(ObjectNotFoundException.class);
        assertThat(throwable).hasMessage("Nous ne retrouvons pas l'entité Paiement avec id 1");
        verify(this.repository,times(1)).findById(1L);
    }

    @Test
    void save() {
        //Given
        given(this.repository.save(this.paiements.get(0))).willReturn(this.paiements.get(0));
        //When
        Paiement result = service.save(this.paiements.get(0));
        //Then
        assertThat(result).isEqualTo(paiements.get(0));
        verify(this.repository,times(1)).save(this.paiements.get(0));
    }

    @Test
    void updateById() {
        //Given
        given(this.repository.findById(1L)).willReturn(Optional.of(this.paiements.get(0)));
        given(this.repository.save(this.paiements.get(0))).willReturn(this.paiements.get(0));
        //When
        Paiement result = service.update(this.paiements.get(0), 1L);
        //Then
        assertThat(result).isEqualTo(paiements.get(0));
        verify(this.repository,times(1)).save(this.paiements.get(0));
        verify(this.repository,times(1)).findById(1L);
    }

    @Test
    void updateByIdNotFound() {
        //Given
        given(this.repository.findById(1L)).willReturn(Optional.empty());
        //When
        Throwable throwable = assertThrows(ObjectNotFoundException.class, () -> service.update(this.paiements.get(0),1L));
        //Then
        assertThat(throwable).isInstanceOf(ObjectNotFoundException.class);
        assertThat(throwable).hasMessage("Nous ne retrouvons pas l'entité Paiement avec id 1");
        verify(this.repository,times(1)).findById(1L);
    }

    @Test
    void deleteById() {
        given(this.repository.findById(1L)).willReturn(Optional.of(this.paiements.get(0)));
        doNothing().when(this.repository).deleteById(1L);
        service.deleteById(1L);
        verify(this.repository,times(1)).findById(1L);
    }

    @Test
    void deleteByIdNotFound() {
        given(this.repository.findById(1L)).willReturn(Optional.empty());
        Throwable throwable = assertThrows(ObjectNotFoundException.class, () -> service.deleteById(1L));
        assertThat(throwable).isInstanceOf(ObjectNotFoundException.class);
        verify(this.repository,times(1)).findById(1L);
    }
}