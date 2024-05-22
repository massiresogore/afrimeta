package com.msr.cg.afrimeta.typeproduit;

import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import com.msr.cg.afrimeta.typeproduit.dto.TypeProduitDto;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TypeProduitServiceTest {
     @InjectMocks
     TypeProduitService typeProduitService;

    @Mock
    TypeProduitRepository typeProduitRepository;

    List<TypeProduit> typeProduits = new ArrayList<>();
    @BeforeEach
    void setUp() {
        TypeProduit typeProduit1 = new TypeProduit(1L,"Mango");
        TypeProduit typeProduit2 = new TypeProduit(2L,"Fruit");
        TypeProduit typeProduit3 = new TypeProduit(3L,"Banana");
        TypeProduit typeProduit4 = new TypeProduit(4L,"Apple");
        TypeProduit typeProduit5 = new TypeProduit(5L,"Orange");

        typeProduits.add(typeProduit1);
        typeProduits.add(typeProduit2);
        typeProduits.add(typeProduit3);
        typeProduits.add(typeProduit4);
        typeProduits.add(typeProduit5);
    }

    @Test
    void findAll() {
        //Given
        given(this.typeProduitRepository.findAll()).willReturn(typeProduits);
        //When
        List<TypeProduit> actualTypeProduits = typeProduitService.findAll();
        //Then
        assertThat(this.typeProduits).isEqualTo(actualTypeProduits);
        assertThat(actualTypeProduits.size()).isEqualTo(typeProduits.size());
        verify(this.typeProduitRepository).findAll();
    }

    @Test
    void findById() {
        //Given
        given(this.typeProduitRepository.findById(1L)).willReturn(Optional.of(this.typeProduits.get(0)));
        //When
        TypeProduit actualTypeProduit = typeProduitService.findById(1L);
        //Then
        assertThat(this.typeProduits.get(0)).isEqualTo(actualTypeProduit);
        assertThat(this.typeProduits.get(0).getNom()).isEqualTo(actualTypeProduit.getNom());
        verify(this.typeProduitRepository).findById(1L);
    }

    @Test
    void findByIdNotFound() {
        //Given
        given(this.typeProduitRepository.findById(1L)).willReturn(Optional.empty());
        //When
        Throwable throwable = catchThrowable(() -> typeProduitService.findById(1L));
        //Then
        assertThat(throwable)
                .hasMessage("Nous ne retrouvons pas l'entité Typeproduit avec id 1")
                .isInstanceOf(ObjectNotFoundException.class);
        verify(this.typeProduitRepository).findById(1L);
    }

    @Test
    void save() {
        //Dto
        TypeProduitDto typeProduitDto = new TypeProduitDto(null,"Mango");
        //object
        TypeProduit typeProduit = new TypeProduit(typeProduitDto.nom());
        //Given
        given(this.typeProduitRepository.save(typeProduit)).willReturn(typeProduit);
        //When
        TypeProduit savedTypeProduit = typeProduitService.save(typeProduit);

        //Then
        assertThat(savedTypeProduit).isEqualTo(typeProduit);
        assertThat(savedTypeProduit.getNom()).isEqualTo(typeProduitDto.nom());
        verify(this.typeProduitRepository).save(typeProduit);
    }

    @Test
    void update() {
        //dto
        TypeProduitDto typeProduitDto = new TypeProduitDto(1L,"Mango");
        //object
        TypeProduit typeProduit = new TypeProduit(typeProduitDto.typeProduitId(),typeProduitDto.nom());

        //Given
        given(this.typeProduitRepository.findById(1L)).willReturn(Optional.of(typeProduit));
        given(this.typeProduitRepository.save(typeProduit)).willReturn(typeProduit);

        //When
        TypeProduit updatedTypeProduit = this.typeProduitService.update(typeProduit,1L);
        //Then
        assertThat(updatedTypeProduit).isEqualTo(typeProduit);
        assertThat(updatedTypeProduit.getNom()).isEqualTo(typeProduit.getNom());
        verify(this.typeProduitRepository).save(typeProduit);
        verify(this.typeProduitRepository).findById(1L);
    }

    @Test
    void updateByIdNotFound() {
        //dto
        TypeProduitDto typeProduitDto = new TypeProduitDto(1L,"Mango");
        //object
        TypeProduit typeProduit = new TypeProduit(typeProduitDto.typeProduitId(),typeProduitDto.nom());

        //Given
        given(this.typeProduitRepository.findById(1L)).willReturn(Optional.empty());
        Throwable throwable = catchThrowable(() -> this.typeProduitService.update(typeProduit,1L));

        assertThat(throwable)
                .hasMessage("Nous ne retrouvons pas l'entité Typeproduit avec id 1")
                 .isInstanceOf(ObjectNotFoundException.class);
        verify(this.typeProduitRepository).findById(1L);
    }

    @Test
    void deleteById() {
        given(this.typeProduitRepository.findById(1L)).willReturn(Optional.of(typeProduits.get(0)));
        doNothing().when(this.typeProduitRepository).deleteById(1L);
        typeProduitService.deleteById(1L);
        verify(this.typeProduitRepository).deleteById(1L);
    }
    @Test
    void deleteByIdNotFound() {
        given(this.typeProduitRepository.findById(1L)).willReturn(Optional.empty());
        Throwable throwable = catchThrowable(() -> typeProduitService.deleteById(1L));
        assertThat(throwable)
                .hasMessage("Nous ne retrouvons pas l'entité Typeproduit avec id 1")
                .isInstanceOf(ObjectNotFoundException.class);
        verify(this.typeProduitRepository).findById(1L);
    }
}