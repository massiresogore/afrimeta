package com.msr.cg.afrimeta.adresse;

import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import com.msr.cg.afrimeta.ville.Ville;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdresseServiceTest {
    @Mock
    AdresseRepository adresseRepository;

    @InjectMocks
    AdresseService adresseService;

    List<Adresse> adresses = new ArrayList<>();
    @BeforeEach
    void setUp() {
        Ville paris = new Ville("Paris");
        Ville berlin = new Ville("Berlin");
        Ville cherry = new Ville("Cherry");
        Ville indonesia = new Ville("Indonesia");

        Adresse adresse = new Adresse("77440",77440,paris);
        Adresse adresse2 = new Adresse("77441",77441,berlin);
        Adresse adresse3 = new Adresse("77442",77442,cherry);
        Adresse adresse4 = new Adresse("77443",77443,indonesia);

        this.adresses.add(adresse);
        this.adresses.add(adresse2);
        this.adresses.add(adresse3);
        this.adresses.add(adresse4);
    }

    @Test
    void findAll() {
        //Given
        given(this.adresseRepository.findAll()).willReturn(adresses);

        //When
        List<Adresse> result = this.adresseService.findAll();

        //Then
        assertThat(this.adresses).hasSameElementsAs(result);
        assertThat(this.adresses).hasSize(4);
        verify(this.adresseRepository,times(1)).findAll();
    }

    @Test
    void findByIdSuccess() {
        Ville paris = new Ville("Paris");
        Adresse adresse = new Adresse(1L,"77440",77440,paris);

        //Given
        given(this.adresseRepository.findById(1L)).willReturn(Optional.of(adresse));

        //When
        Adresse adresse1 = this.adresseService.findById(1L);
        //Then
        assertThat(adresse1.getAdresseId()).isEqualTo(adresse.getAdresseId());
        verify(this.adresseRepository,times(1)).findById(1L);
    }

    @Test
    void findByIdNotFound(){
        //Given
        given(this.adresseRepository.findById(1L)).willReturn(Optional.empty());
        //When
        Throwable exception = catchThrowable(() -> this.adresseService.findById(1L));
        //Then
        assertThat(exception)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Nous ne retrouvons pas l'entité Adresse with id 1 not found avec id 1");
        verify(this.adresseRepository,times(1)).findById(1L);
    }

    @Test
    void save() {
        Ville paris = new Ville("Paris");
        Adresse adresse = new Adresse(1L,"77440",77440,paris);
        //Given
        given(this.adresseRepository.save(adresse)).willReturn(adresse);
        //When
        Adresse adresse1 = this.adresseService.save(adresse);
        //Then
        assertThat(adresse1.getAdresseId()).isEqualTo(adresse.getAdresseId());
        assertThat(adresse1.getCp()).isEqualTo(adresse.getCp());
        verify(this.adresseRepository,times(1)).save(adresse);
    }

    @Test
    void update() {
        Ville paris = new Ville("Paris");
        Adresse adresse = new Adresse(1L,"77440",77440,paris);
        Ville berlin = new Ville("Berlin");
        Adresse update = new Adresse(1L,"77441",77441,berlin);

        given(this.adresseRepository.findById(1L)).willReturn(Optional.of(adresse));
        given(this.adresseRepository.save(adresse)).willReturn(update);

        //When
        Adresse updated = this.adresseService.update(update,1L);

        assertThat(updated.getAdresseId()).isEqualTo(update.getAdresseId());
        assertThat(updated.getCp()).isEqualTo(update.getCp());
        verify(this.adresseRepository,times(1)).findById(1L);
        verify(this.adresseRepository,times(1)).save(adresse);

    }

    @Test
    void delete() {
        Ville paris = new Ville("Paris");
        Adresse adresse = new Adresse(1L,"77440",77440,paris);
        //Given
        given(this.adresseRepository.findById(1L)).willReturn(Optional.of(adresse));
        doNothing().when(this.adresseRepository).deleteById(1L);
        //When
        this.adresseService.deleteById(1L);
        //Then
        verify(this.adresseRepository,times(1)).deleteById(1L);
    }

    @Test
    void deleteNotFound() {
        given(this.adresseRepository.findById(1L)).willReturn(Optional.empty());
        Throwable exception = catchThrowable(() -> this.adresseService.deleteById(1L));
        assertThat(exception)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Nous ne retrouvons pas l'entité Adresse with id 1 not found avec id 1");
        verify(this.adresseRepository,times(1)).findById(1L);
    }
}