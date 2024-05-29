package com.msr.cg.afrimeta.website;

import com.msr.cg.afrimeta.clientUser.ClientUser;
import com.msr.cg.afrimeta.magasin.Magasin;
import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import com.msr.cg.afrimeta.website.dto.WebsiteDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WebsiteServiceTest {
    @InjectMocks
    private WebsiteService service;

    @Mock
    private WebsiteRepository repository;

    List<Website> websites;

    Map<String,String> logoMap = new HashMap<>();

    @BeforeEach
    void setUp() {
        logoMap.put("src/test/resources/logo.png","logo.png");
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

        Magasin magasin1 = new Magasin(1L, "Supermarché BonPrix", "Un supermarché offrant une large gamme de produits alimentaires et ménagers à des prix compétitifs.", clientUser, this.logoMap);
        Magasin magasin2 = new Magasin(2L, "Boulangerie Delice", "Une boulangerie artisanale proposant des pains, pâtisseries et viennoiseries faits maison.",clientUser1,  this.logoMap);
        Magasin magasin3 = new Magasin(3L, "Librairie PageTurner", "Une librairie indépendante avec une grande sélection de livres, magazines et fournitures de bureau.",clientUser2,  this.logoMap);
        Magasin magasin4 = new Magasin(4L, "Boutique ModeTrend", "Une boutique de mode tendance offrant les dernières collections de vêtements et accessoires.", clientUser1, this.logoMap);
        Magasin magasin5 = new Magasin(5L, "Pharmacie SantéPlus", "Une pharmacie offrant une large gamme de médicaments, produits de santé et conseils personnalisés.",clientUser2,  this.logoMap);


        Website website1 = new Website(1L,"http://google.com",magasin1);
        Website website2 = new Website(2L,"http://google.com",magasin2);
        Website website3 = new Website(3L,"http://google.com",magasin3);
        Website website4 = new Website(4L,"http://google.com",magasin4);
        Website website5 = new Website(5L,"http://google.com",magasin5);
        websites = List.of(website1, website2, website3, website4,website5);
    }

    @Test
    void findAll() {
        //Given
        given(this.repository.findAll()).willReturn(websites);
        //When
        List<Website> actualWebsitesList = service.findAll();
        //Then
        assertNotNull(actualWebsitesList);
        assertEquals(websites.size(), actualWebsitesList.size());
        assertEquals(websites.size(),5);
        verify(this.repository,times(1)).findAll();
    }

    @Test
    void findByIdSuccess() {
        //Given
        given(this.repository.findById(1L)).willReturn(Optional.of(this.websites.get(0)));
        //When
        Website actualWebsite = service.findById(1L);
        //Then
        assertNotNull(actualWebsite);
        assertThat(this.websites.get(0)).isEqualTo(actualWebsite);
        assertThat(this.websites.get(0).getWebsiteUrl()).isEqualTo(actualWebsite.getWebsiteUrl());
        verify(this.repository,times(1)).findById(1L);
    }

    @Test
    void findByIdNotFound() {
        //Given
        given(this.repository.findById(Mockito.any(Long.class))).willReturn(Optional.empty());
        //When
        Throwable throwable = catchThrowable(()->service.findById(13L));
        //Then
        assertThat(throwable).isInstanceOf(ObjectNotFoundException.class);
        assertThat(throwable).hasMessage("Nous ne retrouvons pas l'entité Website avec id 13");
        verify(this.repository,times(1)).findById(13L);
    }

    @Test
    void save() {
        ClientUser clientUser = new ClientUser();
        clientUser.setEmail("m@gmail.com");
        clientUser.setPassword("MZMZMZMZMZMZZM");
        clientUser.setRole("ADMIN USER");
        clientUser.setEnable(true);
        Magasin magasin1 = new Magasin(1L, "Supermarché BonPrix", "Un supermarché offrant une large gamme de produits alimentaires et ménagers à des prix compétitifs.", clientUser, this.logoMap);

        //Dto
        WebsiteDto websiteDto = new WebsiteDto(
                null,
                "http://www.afrimeta.com",
                magasin1
        );
        //website object
        Website website = new Website(
                websiteDto.websiteId(),
                websiteDto.websiteUrl(),
                websiteDto.magasin()
                );
        //Given
        given(this.repository.save(website)).willReturn(website);
        //When
        Website savedWebsite = service.save(website);
        //Then
        assertNotNull(savedWebsite);
        assertThat(savedWebsite.getWebsiteUrl()).isEqualTo(website.getWebsiteUrl());
        assertThat(savedWebsite.getMagasin()).isEqualTo(website.getMagasin());
        verify(this.repository,times(1)).save(website);
    }

    @Test
    void update() {
        ClientUser clientUser = new ClientUser();
        clientUser.setEmail("m@gmail.com");
        clientUser.setPassword("MZMZMZMZMZMZZM");
        clientUser.setRole("ADMIN USER");
        clientUser.setEnable(true);
        Magasin magasin1 = new Magasin(1L, "Supermarché BonPrix", "Un supermarché offrant une large gamme de produits alimentaires et ménagers à des prix compétitifs.", clientUser, this.logoMap);

        //Dto
        WebsiteDto websiteDto = new WebsiteDto(
                1L,
                "http://www.afrimetaupdate.com",
                magasin1
        );
        //website object
        Website website = new Website(
                websiteDto.websiteId(),
                websiteDto.websiteUrl(),
                websiteDto.magasin()
        );
        //Given
        given(this.repository.findById(1L)).willReturn(Optional.of(website));
        given(this.repository.save(website)).willReturn(website);
        //When
        Website updatedWebsite = this.service.update(website,1L);
        //Then
        assertNotNull(updatedWebsite);
        assertThat(updatedWebsite.getWebsiteUrl()).isEqualTo(website.getWebsiteUrl());
        assertThat(updatedWebsite.getMagasin()).isEqualTo(website.getMagasin());
        verify(this.repository,times(1)).findById(1L);
    }

    @Test
    void updatewithNoExiqtantId() {
        ClientUser clientUser = new ClientUser();
        clientUser.setEmail("m@gmail.com");
        clientUser.setPassword("MZMZMZMZMZMZZM");
        clientUser.setRole("ADMIN USER");
        clientUser.setEnable(true);
        Magasin magasin1 = new Magasin(1L, "Supermarché BonPrix", "Un supermarché offrant une large gamme de produits alimentaires et ménagers à des prix compétitifs.", clientUser, this.logoMap);

        //Dto
        WebsiteDto websiteDto = new WebsiteDto(
                1L,
                "http://www.afrimetaupdate.com",
                magasin1
        );
        //website object
        Website website = new Website(
                websiteDto.websiteId(),
                websiteDto.websiteUrl(),
                websiteDto.magasin()
        );
        //Given
        given(this.repository.findById(1L)).willReturn(Optional.empty());
        //When
        assertThrows(ObjectNotFoundException.class, ()->service.update(website,1L));
        verify(this.repository,times(1)).findById(1L);
    }

    @Test
    void deleteById() {
        //Given
        given(this.repository.findById(1L)).willReturn(Optional.of(this.websites.get(0)));
        doNothing().when(this.repository).deleteById(1L);

        //When
        this.service.deleteById(1L);
        //Then
        verify(this.repository,times(1)).deleteById(1L);
    }
    @Test
    void deleteByIdNotFound() {
        //Given
        given(this.repository.findById(Mockito.any(Long.class))).willReturn(Optional.empty());
        Throwable throwable = catchThrowable(()->this.service.deleteById(13L));

        assertThat(throwable)
                .hasMessage("Nous ne retrouvons pas l'entité Website avec id 13")
                .isInstanceOf(ObjectNotFoundException.class);
        verify(this.repository,times(1)).findById(13L);
    }

}