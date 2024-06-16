package com.msr.cg.afrimeta.livraison;

import com.msr.cg.afrimeta.clientUser.ClientUser;
import com.msr.cg.afrimeta.commande.Commande;
import com.msr.cg.afrimeta.modelivraison.ModeLivraison;
import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class LivraisonServiceTest {


    @InjectMocks
    LivraisonService service;

    @Mock
    LivraisonRepository repository;

    List<Livraison> livraisons = new ArrayList<>();

    @BeforeEach
    void setUp() {
        //Mode livraison
        ModeLivraison modeLivraison1 = new ModeLivraison();
        modeLivraison1.setNom("Aitel Money");
        //ClientUser
        ClientUser clientUser = new ClientUser("emmano","m@gmail.com","MZMZMZMZMZMZZM",true,"ADMIN USER",null);
        Commande commande1 = new Commande(1L, LocalDateTime.now(),22,"33 rue bandas",22,7,clientUser);

        //Maiement
        Livraison livraison1 = new Livraison(1L, LocalDateTime.now(),"description1",modeLivraison1,commande1);
        Livraison livraison2 = new Livraison(2L, LocalDateTime.now(),"description2",modeLivraison1,commande1);
        Livraison livraison3 = new Livraison(3L, LocalDateTime.now(),"description3",modeLivraison1,commande1);
        livraisons.add(livraison1);
        livraisons.add(livraison2);
        livraisons.add(livraison3);
    }

    @Test
    void findAll() {
        //Given
        given(repository.findAll()).willReturn(livraisons);
        //When
        List<Livraison> result = service.findAll();

        assertThat(result).isEqualTo(livraisons);
        //Then
        verify(this.repository,times(1)).findAll();
    }

    @Test
    void findById() {
        //Given
        given(this.repository.findById(1L)).willReturn(Optional.of(this.livraisons.get(0)));
        //When
        Livraison result = service.findById(1L);
        //Then
        assertThat(result).isEqualTo(livraisons.get(0));
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
        assertThat(throwable).hasMessage("Nous ne retrouvons pas l'entité Livraison avec id 1");
        verify(this.repository,times(1)).findById(1L);
    }

    @Test
    void save() {
        //Given
        given(this.repository.save(this.livraisons.get(0))).willReturn(this.livraisons.get(0));
        //When
        Livraison result = service.save(this.livraisons.get(0));
        //Then
        assertThat(result).isEqualTo(livraisons.get(0));
        verify(this.repository,times(1)).save(this.livraisons.get(0));
    }

    @Test
    void updateById() {
        //Given
        given(this.repository.findById(1L)).willReturn(Optional.of(this.livraisons.get(0)));
        given(this.repository.save(this.livraisons.get(0))).willReturn(this.livraisons.get(0));
        //When
        Livraison result = service.update(this.livraisons.get(0), 1L);
        //Then
        assertThat(result).isEqualTo(livraisons.get(0));
        verify(this.repository,times(1)).save(this.livraisons.get(0));
        verify(this.repository,times(1)).findById(1L);
    }

    @Test
    void updateByIdNotFound() {
        //Given
        given(this.repository.findById(1L)).willReturn(Optional.empty());
        //When
        Throwable throwable = assertThrows(ObjectNotFoundException.class, () -> service.update(this.livraisons.get(0),1L));
        //Then
        assertThat(throwable).isInstanceOf(ObjectNotFoundException.class);
        assertThat(throwable).hasMessage("Nous ne retrouvons pas l'entité Livraison avec id 1");
        verify(this.repository,times(1)).findById(1L);
    }

    @Test
    void deleteById() {
        given(this.repository.findById(1L)).willReturn(Optional.of(this.livraisons.get(0)));
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