package com.msr.cg.afrimeta.magasin;

import com.msr.cg.afrimeta.clientUser.ClientUser;
import com.msr.cg.afrimeta.magasin.dto.MagasinDto;
import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MagasinServiceTest {
    @Mock
    MagasinRepository magasinRepository;

    @InjectMocks
    MagasinService magasinService;

    List<Magasin> magasins = new ArrayList<>();

    @BeforeEach
    void setUp() {
        ClientUser clientUser = new ClientUser();
        clientUser.setEmail("m@gmail.com");
        clientUser.setPassword("MZMZMZMZMZMZZM");
        clientUser.setRole("ADMIN USER");
        clientUser.setEnable(true);

        ClientUser clientUser1 = new ClientUser();
        clientUser1.setEmail("m@gmail.com");
        clientUser1.setPassword("MZMZMZMZMZMZZM");
        clientUser1.setRole("ADMIN USER");
        clientUser1.setEnable(true);

        ClientUser clientUser2 = new ClientUser();
        clientUser2.setEmail("m@gmail.com");
        clientUser2.setPassword("MZMZMZMZMZMZZM");
        clientUser2.setRole("ADMIN USER");
        clientUser2.setEnable(true);

        Magasin magasin1 = new Magasin(1L, "Supermarché BonPrix", "Un supermarché offrant une large gamme de produits alimentaires et ménagers à des prix compétitifs.", clientUser, "lolooo");
        Magasin magasin2 = new Magasin(2L, "Boulangerie Delice", "Une boulangerie artisanale proposant des pains, pâtisseries et viennoiseries faits maison.",clientUser1, "logo_delice.png");
        Magasin magasin3 = new Magasin(3L, "Librairie PageTurner", "Une librairie indépendante avec une grande sélection de livres, magazines et fournitures de bureau.",clientUser2, "logo_pageturner.png");
        Magasin magasin4 = new Magasin(4L, "Boutique ModeTrend", "Une boutique de mode tendance offrant les dernières collections de vêtements et accessoires.", clientUser1,"logo_modetrend.png");
        Magasin magasin5 = new Magasin(5L, "Pharmacie SantéPlus", "Une pharmacie offrant une large gamme de médicaments, produits de santé et conseils personnalisés.",clientUser2, "logo_santeplus.png");

        this.magasins.add(magasin1);
        this.magasins.add(magasin2);
        this.magasins.add(magasin3);
        this.magasins.add(magasin4);
        this.magasins.add(magasin5);
        this.magasins.add(magasin5);
    }

    @Test
    void findAll() {
        //given
        given(this.magasinRepository.findAll()).willReturn(this.magasins);
        //When
        List<Magasin> magasins = magasinService.findAll();
        //Then
        assertNotNull(magasins);
        assertFalse(magasins.isEmpty());
        assertThat(this.magasins.size()).isEqualTo(6);
        verify(this.magasinRepository,times(1)).findAll();
    }

    @Test
    void findByIdSuccess() {
        //Given
        given(this.magasinRepository.findById(1L)).willReturn(Optional.of(this.magasins.get(0)));
        //When
        Magasin magasin = magasinService.findById(1L);
        //Then
        assertNotNull(magasin);
        assertThat(magasin.getLibele()).isEqualTo(magasins.get(0).getLibele());
        assertThat(magasin.getClientUser()).isEqualTo(magasins.get(0).getClientUser());
        verify(this.magasinRepository,times(1)).findById(1L);
    }

    @Test
    void findByIdNotFound() {
        //Given
        given(this.magasinRepository.findById(12L)).willReturn(Optional.empty());
        //When
        Throwable throwable = catchThrowable(() -> magasinService.findById(12L));
        assertThat(throwable)
                .hasMessage("Nous ne retrouvons pas l'entité Magasin avec id 12")
                .isInstanceOf(ObjectNotFoundException.class);
        //Then
        verify(this.magasinRepository,times(1)).findById(12L);
    }

    @Test
    void save() {
        ClientUser clientUser = new ClientUser();
        clientUser.setEmail("m@gmail.com");
        clientUser.setPassword("MZMZMZMZMZMZZM");
        clientUser.setRole("ADMIN USER");
        clientUser.setEnable(true);
        //receive
        MagasinDto magasinDto = new MagasinDto(
                null,
                "Amazon",
               "Amazon description",
                "Amazo Logo",
                clientUser
        );

        //convert to magasin
       Magasin magasinToSave =  new Magasin(
                magasinDto.magasinId(),
                magasinDto.libele(),
                magasinDto.description(),
                magasinDto.clientUser(),
                magasinDto.logo()
        );

        //Given
        given(this.magasinRepository.save(magasinToSave)).willReturn(magasinToSave);
        //When
        Magasin savedMagasin = magasinService.save(magasinToSave);
        //Then
        assertNotNull(savedMagasin);
        assertThat(savedMagasin.getClientUser()).isEqualTo(clientUser);
        assertThat(savedMagasin.getClientUser().getEmail()).isEqualTo(clientUser.getEmail());
        assertThat(magasinToSave.getLibele()).isEqualTo(magasinDto.libele());

        verify(this.magasinRepository,times(1)).save(magasinToSave);

    }

    @Test
    void updateByIdSuccess() {
        ClientUser clientUser = new ClientUser();
        clientUser.setEmail("m@gmail.com");
        clientUser.setPassword("MZMZMZMZMZMZZM");
        clientUser.setRole("ADMIN USER");
        clientUser.setEnable(true);
        //receive
        MagasinDto magasinDto = new MagasinDto(
                1L,
                "Amazon update",
                "Amazon description update",
                "Amazo Logo",
                clientUser
        );

        //convert to magasin
        Magasin magasinToSave =  new Magasin(
                magasinDto.magasinId(),
                magasinDto.libele(),
                magasinDto.description(),
                magasinDto.clientUser(),
                magasinDto.logo()
        );

        //Given
        given(this.magasinRepository.findById(1L)).willReturn(Optional.of(magasinToSave));
        given(this.magasinRepository.save(magasinToSave)).willReturn(magasinToSave);

        //When
        Magasin savedMagasin = magasinService.update(magasinToSave,1L);
        //Then
        assertNotNull(savedMagasin);
        assertThat(savedMagasin.getClientUser()).isEqualTo(clientUser);
        assertThat(savedMagasin.getClientUser().getEmail()).isEqualTo(clientUser.getEmail());
        assertThat(magasinToSave.getLibele()).isEqualTo(magasinDto.libele());

        verify(this.magasinRepository,times(1)).findById(1L);
        verify(this.magasinRepository,times(1)).save(magasinToSave);

    }

    @Test
    void updateByIdNotFound() {
        ClientUser clientUser = new ClientUser();
        clientUser.setEmail("m@gmail.com");
        clientUser.setPassword("MZMZMZMZMZMZZM");
        clientUser.setRole("ADMIN USER");
        clientUser.setEnable(true);
        //receive
        MagasinDto magasinDto = new MagasinDto(
                1L,
                "Amazon update",
                "Amazon description update",
                "Amazo Logo",
                clientUser
        );

        //convert to magasin
        Magasin magasinToSave =  new Magasin(
                magasinDto.magasinId(),
                magasinDto.libele(),
                magasinDto.description(),
                magasinDto.clientUser(),
                magasinDto.logo()
        );

        //Given
        given(this.magasinRepository.findById(1L)).willReturn(Optional.empty());

        //When
        Throwable throwable = catchThrowable(() -> magasinService.update(magasinToSave,1L));
        //Then
        assertThat(throwable)
                .hasMessage("Nous ne retrouvons pas l'entité Magasin avec id 1")
                .isInstanceOf(ObjectNotFoundException.class);
        verify(this.magasinRepository,times(1)).findById(1L);

    }

    @Test
    void deleteById() {
        given(this.magasinRepository.findById(1L)).willReturn(Optional.of(this.magasins.get(0)));
        doNothing().when(this.magasinRepository).deleteById(1L);
        this.magasinService.deleteById(1L);
        verify(this.magasinRepository,times(1)).deleteById(1L);
    }

    @Test
    void deleteByIdNotFound() {
        given(this.magasinRepository.findById(13L)).willReturn(Optional.empty());

        //When
        Throwable throwable = catchThrowable(() -> magasinService.deleteById(13L));
        //Then
        assertThat(throwable)
                .hasMessage("Nous ne retrouvons pas l'entité Magasin avec id 13")
                .isInstanceOf(ObjectNotFoundException.class);
        verify(this.magasinRepository,times(1)).findById(13L);
    }

}