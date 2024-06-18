package com.msr.cg.afrimeta.website;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msr.cg.afrimeta.clientUser.ClientUser;
import com.msr.cg.afrimeta.magasin.Magasin;
import com.msr.cg.afrimeta.magasin.MagasinService;
import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import com.msr.cg.afrimeta.website.dto.WebsiteDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
//@AutoConfigureMockMvc(addFilters = false) //désactive la sécurité spring
@ActiveProfiles("dev")
class WebsiteControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private WebsiteService websiteService;

    @MockBean
    MagasinService magasinService;

    @Autowired
    ObjectMapper objectMapper;

    List<Website> websites = new ArrayList<>();

    @Value("${api.endpoint.base-url}")
    String url;

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
       // websites = List.of(website1, website2, website3, website4,website5);
        websites.add(website1);
        websites.add(website2);
        websites.add(website3);
        websites.add(website4);
        websites.add(website5);
    }
    @Test
    void getAllWebsites() throws Exception {
        //Given
        given(this.websiteService.findAll()).willReturn(websites);
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.get(url+"/website"))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("tous les websites"))
                .andExpect(jsonPath("$.data[0].websiteUrl").value(websites.get(0).getWebsiteUrl()))
                .andExpect(jsonPath("$.data.size()").value(websites.size()));
    }

    @Test
    void getWebsiteById() throws Exception {

        given(this.websiteService.findById(1L)).willReturn(websites.get(0));
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.get(url+"/website/{websiteId}",1))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("website trouvé"))
                .andExpect(jsonPath("$.data.websiteUrl").value(websites.get(0).getWebsiteUrl()));
    }

    @Test
    void getWebsiteByIdNotFound() throws Exception {

        given(this.websiteService.findById(1L)).willThrow(new ObjectNotFoundException("website", 1L));
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.get(url+"/website/{websiteId}",1))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Nous ne retrouvons pas l'entité website avec id 1"))
                .andExpect(jsonPath("$.data", Matchers.nullValue()));
    }

    @Test
    void updateWebsite() throws Exception {
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
        String jsonWebsite = objectMapper.writeValueAsString(websiteDto);

        given(this.websiteService.update(Mockito.any(Website.class),eq(1L))).willReturn(websites.get(0));
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.patch(url+"/website/{websiteId}",1)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonWebsite)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("website mis à jour"))
                .andExpect(jsonPath("$.data.websiteUrl").value(websites.get(0).getWebsiteUrl()));
    }

    @Test
    void deleteWebsite() throws Exception {
        doNothing().when(this.websiteService).deleteById(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete(url+"/website/{websiteId}",1)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("website supprimé"))
                .andExpect(jsonPath("$.data",Matchers.nullValue()));
    }

    @Test
    void deleteWebsiteByIdNotFound() throws Exception {
        doThrow(new ObjectNotFoundException("website", 1L)).when(this.websiteService).deleteById(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete(url+"/website/{websiteId}",1)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Nous ne retrouvons pas l'entité website avec id 1"))
                .andExpect(jsonPath("$.data", Matchers.nullValue()));
    }

    @Test
    void saveWebsite() throws Exception {
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
        String jsonWebsite = objectMapper.writeValueAsString(websiteDto);
        //website object
        Website website = new Website(
                websiteDto.websiteId(),
                websiteDto.websiteUrl(),
                websiteDto.magasin()
        );

        given(this.magasinService.findById(1L)).willReturn(magasin1);

        given(this.websiteService.save(Mockito.any(Website.class))).willReturn(website);
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.post(url+"/website/{magasinId}",1)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonWebsite)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("website cré"))
                .andExpect(jsonPath("$.data.websiteUrl").value(website.getWebsiteUrl()));
    }
}