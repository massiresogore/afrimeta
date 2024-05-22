package com.msr.cg.afrimeta.taille;

import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import com.msr.cg.afrimeta.taille.dto.TailleDto;
import com.msr.cg.afrimeta.typeproduit.TypeProduit;
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

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TailleServiceTest {
    @InjectMocks
    TailleService tailleService;
    @Mock
    TailleRepository tailleRepository;


    List<Taille> tailles = new ArrayList<>();
    @BeforeEach
    void setUp() {
        Taille taille1 = new Taille(1L,"Mango");
        Taille taillet2 = new Taille(2L,"Fruit");
        Taille taillet3 = new Taille(3L,"Banana");
        Taille taillet4 = new Taille(4L,"Apple");
        Taille taillet5 = new Taille(5L,"Orange");

        tailles.add(taille1);
        tailles.add(taillet2);
        tailles.add(taillet3);
        tailles.add(taillet4);
        tailles.add(taillet5);
    }

    @Test
    void findAll() {
        //given
        given(this.tailleRepository.findAll()).willReturn(tailles);
        //When
        List<Taille> result = tailleService.findAll();
        //Then
        assertThat(result).isEqualTo(tailles);
        assertThat(result.size()).isEqualTo(5);
        assertNotNull(tailles);
        verify(this.tailleRepository,times(1)).findAll();
    }

    @Test
    void findById() {
        Taille taille = new Taille(1L,"Mango");

        //Given
        given(this.tailleRepository.findById(1L)).willReturn(Optional.of(taille));
        //When
        Taille result = tailleService.findById(1L);
        //Then
        assertThat(result).isEqualTo(taille);
        assertThat(result.getNom()).isEqualTo(taille.getNom());
        verify(this.tailleRepository,times(1)).findById(1L);
    }

    @Test
    void findByIdNotFound() {
        Taille taille = new Taille(12L,"Mango");

        //Given
        given(this.tailleRepository.findById(12L)).willThrow(new ObjectNotFoundException(taille.getClass().getSimpleName(),12L));
        //When
        Throwable throwable = catchThrowable(()-> this.tailleRepository.findById(12L));
        //Then
        assertThat(throwable)
                .hasMessage("Nous ne retrouvons pas l'entité Taille avec id 12")
                .isInstanceOf(ObjectNotFoundException.class);
        verify(this.tailleRepository,times(1)).findById(12L);
    }

    @Test
    void save() {
        TailleDto tailleDto = new TailleDto(null,"Mango");
        Taille taille = new Taille(tailleDto.nom());
        given(this.tailleRepository.save(taille)).willReturn(taille);
        Taille savedTaille = tailleService.save(taille);
        assertThat(savedTaille).isEqualTo(taille);
        assertThat(savedTaille.getNom()).isEqualTo(taille.getNom());
        verify(this.tailleRepository,times(1)).save(taille);

    }

    @Test
    void update() {
        TailleDto tailleDto = new TailleDto(1L,"Mango");
        Taille taille = new Taille(tailleDto.nom()+ " update");
        given(this.tailleRepository.save(taille)).willReturn(taille);
        Taille savedTaille = tailleService.save(taille);

        assertThat(savedTaille).isEqualTo(taille);
        assertThat(savedTaille.getNom()).isEqualTo(taille.getNom());
        verify(this.tailleRepository,times(1)).save(taille);
    }

    @Test
    void updateByIdNotFound() {
        TailleDto tailleDto = new TailleDto(1L,"Mango");
        Taille taille = new Taille(tailleDto.tailleId(),tailleDto.nom()+ " update");
        given(this.tailleRepository.findById(1L)).willReturn(Optional.empty());

        Throwable throwable = catchThrowable(()-> tailleService.update(taille,1L));
        assertThat(throwable)
                .hasMessage("Nous ne retrouvons pas l'entité taille avec id 1")
        .isInstanceOf(ObjectNotFoundException.class);

        verify(this.tailleRepository,times(1)).findById(1L);
    }

    @Test
    void deleteById() {
        //Given
        given(this.tailleRepository.findById(1L)).willReturn(Optional.of(new Taille(1L,"Mango")));
        doNothing().when(this.tailleRepository).deleteById(1L);
        //When
        tailleService.deleteById(1L);
        //Then
        verify(this.tailleRepository,times(1)).deleteById(1L);
    }

    @Test
    void deleteByIdNotFound() {
        //Given
        given(this.tailleRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, ()-> tailleService.deleteById(1L));

        verify(this.tailleRepository,times(1)).findById(1L);
    }
}