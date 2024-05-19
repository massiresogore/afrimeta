/*
package com.msr.cg.afrimeta.magasin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class MagasinServiceTest {

    @Mock
    MagasinRepository magasinRepository;

    @MockBean
    MagasinService magasinService;

    List<Magasin> magasins = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Magasin magasin1 = new Magasin(1L, "Supermarché Bio", "https://exemple.com/logo_supermarche_bio.jpg", "Votre destination pour des produits biologiques de haute qualité.", 1001);
        Magasin magasin2 = new Magasin(2L, "Librairie de Quartier", "https://exemple.com/logo_librairie.jpg", "Une librairie conviviale offrant une large sélection de livres pour tous les âges.", 1002);
        Magasin magasin3 = new Magasin(3L, "Boutique de Vêtements Vintage", "https://exemple.com/logo_boutique_vintage.jpg", "Trouvez des vêtements uniques et stylés dans notre collection vintage.", 1003);
        Magasin magasin4 = new Magasin(4L, "Magasin d'Électronique TechGear", "https://exemple.com/logo_techgear.jpg", "Découvrez les dernières innovations technologiques et gadgets dans notre magasin.", 1004);
        Magasin magasin5 = new Magasin(5L, "Boulangerie Artisanale Le Pain Doré", "https://exemple.com/logo_pain_dore.jpg", "Du pain frais et des pâtisseries délicieuses tous les jours.", 1005);

        magasins.add(magasin1);
        magasins.add(magasin2);
        magasins.add(magasin3);
        magasins.add(magasin4);
        magasins.add(magasin5);

    }

   */
/* @Test
    void findAll() {
        //Given
        given(this.magasinRepository.findAll()).willReturn(this.magasins);
        // When
        List<Magasin> magasinList=  this.magasinService.findAll();
        //Then
        assertThat(this.magasins).isEqualTo(magasinList);
        verify(this.magasinRepository,times(1)).findAll();
    }*//*


    @Test
    void findById() {
    }
//
//    @Test
//    void save() {
//        Magasin magasin1 = new Magasin("Supermarché Bio", "https://exemple.com/logo_supermarche_bio.jpg", "Votre destination pour des produits biologiques de haute qualité.", 1001);
//
//        //Given
//        given(this.magasinRepository.save(magasin1)).willReturn(magasin1);
//        //When
//        Magasin savedMagasin = this.magasinService.save(magasin1);
//        //Then
//        assertNotNull(savedMagasin);
//        assertThat(savedMagasin.getLibele()).isEqualTo(magasin1.getLibele());
//        verify(this.magasinRepository, times(1)).save(magasin1);
//
//    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }
}*/
