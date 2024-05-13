package com.msr.cg.afrimeta.ville;

import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class VilleServiceTest {

    @Mock
    VilleRepository villeRepository;

    @InjectMocks
    VilleService villeService;

    // Liste de ville attribute
    List<Ville> villeList = new ArrayList<>();
    @BeforeEach
    void setUp() {
        //Initialise fake liste Ville
        Ville paris = new Ville("Paris");
        Ville mossaka = new Ville("mossaka");
        Ville allemagne = new Ville("allemagne");
        Ville usa = new Ville("usa");
        Ville imfondo = new Ville("imfondo");
        villeList.add(paris);
        villeList.add(mossaka);
        villeList.add(allemagne);
        villeList.add(usa);
        villeList.add(imfondo);

    }

    @Test
    void findAllSuccess() {
        //Given
        //Lorqu'on appelle villeService dans notre context doit retourner villeList
        given(this.villeRepository.findAll()).willReturn(villeList);

        //When
        // Appelle de findAll de villeService
        List<Ville> actualVilles = villeService.findAll();

        //Then
        //Vérifie si la valeur de actualVilles == villeList
        assertThat(actualVilles.size()).isEqualTo(villeList.size());
        //Invoque une fois findAll() de villeService
        verify(this.villeRepository,times(1)).findAll();
    }


    @Test
    void saveSuccess() {
        Ville paris = new Ville("Paris");

        //Given
        given(this.villeRepository.save(paris)).willReturn(paris);

        //When
        Ville savedVille = villeService.save(paris);

        //Then
        assertThat(savedVille.getNom()).isEqualTo(paris.getNom());
        verify(this.villeRepository,times(1)).save(paris);
    }

    @Test
    void findById() {
        Ville paris = new Ville(Long.parseLong("1"),"Paris");

        //Given
        given(this.villeRepository.findById(Long.parseLong("1"))).willReturn(Optional.of(paris));

        //When
        Ville actualVille = villeService.findById(1);

        //Then
        assertThat(actualVille.getVilleId()).isEqualTo(paris.getVilleId());
        assertThat(actualVille.getNom()).isEqualTo(paris.getNom());
        verify(this.villeRepository,times(1)).findById(Long.valueOf("1"));

    }

    @Test
    void findByIdNotFound()
    {
        //Given
        given(this.villeRepository.findById(Mockito.any(Long.class))).willReturn(Optional.empty());

        //When
        Throwable throwable = catchThrowable(() -> villeService.findById(12));

        //Then
        assertThat(throwable)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Nous ne retrouvons pas l'entité Ville avec id 12");
        verify(this.villeRepository,Mockito.times(1)).findById(Long.valueOf("12"));
    }

    @Test
    void update() {
        Ville paris = new Ville(1L,"Paris");
        Ville updateParis = new Ville(1L,"Paris à jour");

        //Given
        given(this.villeRepository.findById(1L)).willReturn(Optional.of(paris));
        given(this.villeRepository.save(paris)).willReturn(paris);

        //WHEN
        Ville updated = villeService.update(updateParis,1L);

        //Then
        assertThat(updated.getVilleId()).isEqualTo(updateParis.getVilleId());
        assertThat(updated.getNom()).isEqualTo(updateParis.getNom());
        verify(this.villeRepository,times(1)).findById(1L);
        verify(this.villeRepository,times(1)).save(paris);
    }
    @Test
    void deleteById() {
        Ville paris = new Ville();
        paris.setVilleId(1L);
        paris.setNom("paris");

        //Given
        given(this.villeRepository.findById(1L)).willReturn(Optional.of(paris));
        doNothing().when(this.villeRepository).deleteById(1L);

        //When
        this.villeService.deleteById(1L);

        //Then
        verify(this.villeRepository,times(1)).deleteById(1L);
    }

    @Test
    void deleteByIdNotExist()
    {
        //Given
        given(this.villeRepository.findById(Mockito.any())).willReturn(Optional.empty());

        //When
        Throwable throwable = catchThrowable(()->this.villeService.findById(22));

        //Then
        assertThat(throwable)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Nous ne retrouvons pas l'entité Ville avec id 22");
        verify(this.villeRepository,Mockito.times(1)).findById(22L);
    }


}