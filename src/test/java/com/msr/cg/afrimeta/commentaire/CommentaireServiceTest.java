package com.msr.cg.afrimeta.commentaire;

import com.msr.cg.afrimeta.clientUser.ClientUser;
import com.msr.cg.afrimeta.produit.Produit;
import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import com.msr.cg.afrimeta.website.Website;
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
class CommentaireServiceTest {


    @InjectMocks
    CommentaireService service;

    @Mock
    CommentaireRepository repository;

    List<Commentaire> commentaires = new ArrayList<>();

    @BeforeEach
    void setUp() {
       //Client
        ClientUser clientUser = new ClientUser();
        clientUser.setEmail("m@gmail.com");
        clientUser.setPassword("MZMZMZMZMZMZZM");
        clientUser.setRole("ADMIN USER");
        clientUser.setEnable(true);

        //Produit
        Website website1 = new Website(1L,"http://google.com",null);
        Produit produit1 = new Produit(1L, "Apple iPhone 14", "Latest model with advanced features", 50, 999.99, null, null, null, website1);

        //commentaire
        Commentaire commentaire1 = new Commentaire(LocalDateTime.now(),"J'adore ce produit",clientUser,produit1);
        Commentaire commentaire2 = new Commentaire(LocalDateTime.now(),"J'adore ce produit vraiment",clientUser,produit1);
        Commentaire commentaire3 = new Commentaire(LocalDateTime.now(),"J'adore ce produit vraiment, c'est trop génial",clientUser,produit1);
        commentaires.add(commentaire1);
        commentaires.add(commentaire2);
        commentaires.add(commentaire3);
    }

    @Test
    void findAll() {
        //Given
        given(this.repository.findAll()).willReturn(commentaires);
        //When
        List<Commentaire> result = service.findAll();

        assertThat(result).isEqualTo(commentaires);
        //Then
        verify(this.repository,times(1)).findAll();
    }

    @Test
    void findById() {
        //Given
        given(this.repository.findById(1L)).willReturn(Optional.of(this.commentaires.get(0)));
        //When
        Commentaire result = service.findById(1L);
        //Then
        assertThat(result).isEqualTo(commentaires.get(0));
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
        assertThat(throwable).hasMessage("Nous ne retrouvons pas l'entité Commentaire avec id 1");
        verify(this.repository,times(1)).findById(1L);
    }

    @Test
    void save() {
        //Given
        given(this.repository.save(this.commentaires.get(0))).willReturn(this.commentaires.get(0));
        //When
        Commentaire result = service.save(this.commentaires.get(0));
        //Then
        assertThat(result).isEqualTo(commentaires.get(0));
        verify(this.repository,times(1)).save(this.commentaires.get(0));
    }

    @Test
    void updateById() {
        //Given
        given(this.repository.findById(1L)).willReturn(Optional.of(this.commentaires.get(0)));
        given(this.repository.save(this.commentaires.get(0))).willReturn(this.commentaires.get(0));
        //When
        Commentaire result = service.update(this.commentaires.get(0), 1L);
        //Then
        assertThat(result).isEqualTo(commentaires.get(0));
        verify(this.repository,times(1)).save(this.commentaires.get(0));
        verify(this.repository,times(1)).findById(1L);
    }

    @Test
    void updateByIdNotFound() {
        //Given
        given(this.repository.findById(1L)).willReturn(Optional.empty());
        //When
        Throwable throwable = assertThrows(ObjectNotFoundException.class, () -> service.update(this.commentaires.get(0),1L));
        //Then
        assertThat(throwable).isInstanceOf(ObjectNotFoundException.class);
        assertThat(throwable).hasMessage("Nous ne retrouvons pas l'entité Commentaire avec id 1");
        verify(this.repository,times(1)).findById(1L);
    }

    @Test
    void deleteById() {
        given(this.repository.findById(1L)).willReturn(Optional.of(this.commentaires.get(0)));
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