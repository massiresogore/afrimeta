package com.msr.cg.afrimeta.commande;

import com.msr.cg.afrimeta.clientUser.ClientUser;
import com.msr.cg.afrimeta.facture.Facture;
import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CommandeServiceTest {

    @InjectMocks
    CommandeService service;

    @Mock
    CommandeRepository repository;

    List<Commande> commandes = new ArrayList<>();

    @BeforeEach
    void setUp() {
        //ClientUser
        ClientUser clientUser = new ClientUser("emmano","m@gmail.com","MZMZMZMZMZMZZM",true,"ADMIN USER",null);

        //Facture
        Facture facture = new Facture(1L, LocalDate.now(),22,22,2);

        Commande commande1 = new Commande(1L, LocalDateTime.now(),22,"33 rue bandas",22,7,clientUser);
        Commande commande2 = new Commande(2L,LocalDateTime.now(),55,"98 rue dohomet",12,9,clientUser);
        Commande commande3 = new Commande(3L,LocalDateTime.now(),7,"8 rue loango",33,4,clientUser);
        commandes.add(commande1);
        commandes.add(commande2);
        commandes.add(commande3);
    }

    @Test
    void findAll() {
        //Given
        given(repository.findAll()).willReturn(commandes);
        //When
        List<Commande> actual = service.findAll();
        //Then
        assertThat(actual).isEqualTo(commandes);
        verify(this.repository,times(1)).findAll();
    }

    @Test
    void findById() {
        //Given
        given(this.repository.findById(1L)).willReturn(Optional.of(this.commandes.get(0)));
        //When
        Commande actual = service.findById(1L);
        //Then
        assertThat(actual).isEqualTo(commandes.get(0));
        assertThat(actual.getPrixTotal()).isEqualTo(commandes.get(0).getPrixTotal());
        assertThat(actual.getCommandeId()).isEqualTo(1L);
        verify(this.repository,times(1)).findById(1L);
    }

    @Test
    void findByIdNotFound() {
        //Given
        given(this.repository.findById(12L)).willReturn(Optional.empty());
        //When
        Throwable throwable = catchThrowable(()->service.findById(12L));
        //Then
        assertThat(throwable)
                .hasMessage("Nous ne retrouvons pas l'entité Commande avec id 12")
                .isInstanceOf(ObjectNotFoundException.class);
        verify(this.repository,times(1)).findById(12L);
    }

    @Test
    void save() {
        //ClientUser
        ClientUser clientUser = new ClientUser("emmano","m@gmail.com","MZMZMZMZMZMZZM",true,"ADMIN USER",null);

        //Facture
        Facture facture = new Facture(1L, LocalDate.now(),22,22,2);

        Commande commande1 = new Commande(1L,LocalDateTime.now(),22,"33 rue bandas",22,7,clientUser);

        //given
        given(this.repository.save(Mockito.any(Commande.class))).willReturn(commande1);

        //When
        Commande actual = service.save(commande1);

        //Then
        assertThat(actual).isEqualTo(commande1);
        assertThat(actual.getPrixTotal()).isEqualTo(commande1.getPrixTotal());
        assertThat(actual.getCommandeId()).isEqualTo(1L);
        verify(this.repository,times(1)).save(commande1);
    }

    @Test
    void update() {
        //ClientUser
        ClientUser clientUser = new ClientUser("emmano updated","m@gmail.com","MZMZMZMZMZMZZM",true,"ADMIN USER",null);

        //Facture
        Facture facture = new Facture(1L, LocalDate.now(),22,22,2);

        Commande commande1 = new Commande(1L,LocalDateTime.now(),22,"33 rue bandas",22,7,clientUser);

        //given
        given(this.repository.findById(1L)).willReturn(Optional.of(commande1));
        given(this.repository.save(Mockito.any(Commande.class))).willReturn(commande1);

        //When
        Commande actual = service.update(commande1,1L);

        //Then
        assertThat(actual).isEqualTo(commande1);
        assertThat(actual.getPrixTotal()).isEqualTo(commande1.getPrixTotal());
        assertThat(actual.getCommandeId()).isEqualTo(1L);
        verify(this.repository,times(1)).save(commande1);
    }

    @Test
    void updateithIdNotFound() {
        //ClientUser
        ClientUser clientUser = new ClientUser(1L,"emmano updated","m@gmail.com","MZMZMZMZMZMZZM",true,"ADMIN USER",null);

        //Facture
        Facture facture = new Facture(1L, LocalDate.now(),22,22,2);

        Commande commande1 = new Commande(12L,LocalDateTime.now(),22,"33 rue bandas",22,7,clientUser);

        //given
        given(this.repository.findById(12L)).willReturn(Optional.empty());

        //When
        Throwable throwable = catchThrowable(()->service.update(commande1,12L));
        //Then
        assertThat(throwable)
                .hasMessage("Nous ne retrouvons pas l'entité Commande avec id 12")
                .isInstanceOf(ObjectNotFoundException.class);
        verify(this.repository,times(1)).findById(12L);
    }

    @Test
    void deleteById() {

        //Given
        given(this.repository.findById(1L)).willReturn(Optional.of(commandes.get(0)));
        doNothing().when(this.repository).deleteById(1L);
        //When
        service.deleteById(1L);
        //Then
        verify(this.repository,times(1)).findById(1L);
        verify(this.repository,times(1)).deleteById(1L);
    }

    @Test
    void deleteByIdNotFound() {

        //Given
        given(this.repository.findById(22L)).willReturn(Optional.empty());

        //When
        Throwable throwable = catchThrowable(()->service.deleteById(22L));
        //Then
        assertThat(throwable)
                .hasMessage("Nous ne retrouvons pas l'entité Commande avec id 22")
                .isInstanceOf(ObjectNotFoundException.class);
        verify(this.repository,times(1)).findById(22L);
    }
}