package com.msr.cg.afrimeta.magasin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msr.cg.afrimeta.clientUser.ClientUser;
import com.msr.cg.afrimeta.magasin.dto.MagasinDto;
import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
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
import org.springframework.data.crossstore.ChangeSetPersister;
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
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("dev")
class MagasinControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MagasinService service;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${api.endpoint.base-url}")
    String url;

    List<Magasin> magasins = new ArrayList<>();
    Map<String,String> logoMap = new HashMap<>();


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

        //Craete logo

        logoMap.put("src/test/resources/magasin.json", "logo.png");


        Magasin magasin1 = new Magasin(1L, "Supermarché BonPrix", "Un supermarché offrant une large gamme de produits alimentaires et ménagers à des prix compétitifs.", clientUser, this.logoMap);
        Magasin magasin2 = new Magasin(2L, "Boulangerie Delice", "Une boulangerie artisanale proposant des pains, pâtisseries et viennoiseries faits maison.",clientUser1, this.logoMap);
        Magasin magasin3 = new Magasin(3L, "Librairie PageTurner", "Une librairie indépendante avec une grande sélection de livres, magazines et fournitures de bureau.",clientUser2, this.logoMap);
        Magasin magasin4 = new Magasin(4L, "Boutique ModeTrend", "Une boutique de mode tendance offrant les dernières collections de vêtements et accessoires.", clientUser1,this.logoMap);
        Magasin magasin5 = new Magasin(5L, "Pharmacie SantéPlus", "Une pharmacie offrant une large gamme de médicaments, produits de santé et conseils personnalisés.",clientUser2, this.logoMap);

        this.magasins.add(magasin1);
        this.magasins.add(magasin2);
        this.magasins.add(magasin3);
        this.magasins.add(magasin4);
        this.magasins.add(magasin5);
        this.magasins.add(magasin5);
    }

    @Test
    void getAllMagasins() throws Exception {
        //Given
        given(this.service.findAll()).willReturn(magasins);
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.get(url+"/magasins")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("tous les magasins"))
                .andExpect(jsonPath("$.data", Matchers.hasSize(this.magasins.size())))
        ;
    }

    @Test
    void getMagasinById() throws Exception {
        ClientUser clientUser = new ClientUser();
        clientUser.setEmail("m@gmail.com");
        clientUser.setPassword("MZMZMZMZMZMZZM");
        clientUser.setRole("ADMIN USER");
        clientUser.setEnable(true);
        Magasin magasin = new Magasin();
        magasin.setClientUser(clientUser);
        magasin.setDescription("description");
        magasin.setMagasinId(1L);
        magasin.setLibele("libekz");
        magasin.setLogo(this.logoMap);

        //Given
        given(this.service.findById(1L)).willReturn(magasin);
        //When
        mockMvc.perform(MockMvcRequestBuilders.get(url+"/magasins/{magasindId}",1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("magasin trouvé"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.libele").value(magasin.getLibele()))
                .andExpect(jsonPath("$.data.description").value(magasin.getDescription()))
                .andExpect(jsonPath("$.data.logo").value(magasin.getLogo()))
        ;

    }

    @Test
    void getMagasinByIdNotFound() throws Exception {


        //Given
        given(this.service.findById(1L)).willThrow(new ObjectNotFoundException("magasin",1L));
        //When
        mockMvc.perform(MockMvcRequestBuilders.get(url+"/magasins/{magasindId}",1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Nous ne retrouvons pas l'entité magasin avec id 1"))
                .andExpect(jsonPath("$.data").doesNotExist())
        ;

    }

    @Test
    void updateMagasinByIdSucssess() throws Exception {
        ClientUser clientUser = new ClientUser();
        clientUser.setEmail("m@gmail.com");
        clientUser.setPassword("MZMZMZMZMZMZZM");
        clientUser.setRole("ADMIN USER");
        clientUser.setEnable(true);

        MagasinDto magasinDto = new MagasinDto(
                null,
                "libekz",
                "description",
                this.logoMap,
                clientUser
        );


        Magasin magasin = new Magasin();
        magasin.setClientUser(clientUser);
        magasin.setDescription(magasinDto.description());
        magasin.setLibele(magasin.getLibele());
        magasin.setLogo(magasin.getLogo());
        //jso,Magasin
        String jsonmagasoin = objectMapper.writeValueAsString(magasinDto);

        //Given
        given(this.service.update(Mockito.any(Magasin.class),eq(1L))).willReturn(magasin);

        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.patch(url+"/magasins/{magasindId}",1)
                        .content(jsonmagasoin)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("magasin mis à jours"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.libele").value(magasin.getLibele()))
                .andExpect(jsonPath("$.data.description").value(magasin.getDescription()))
                .andExpect(jsonPath("$.data.logo").value(magasin.getLogo()));
    }

    @Test
    void updateMagasinByIdNotFound() throws Exception {
        ClientUser clientUser = new ClientUser();
        clientUser.setEmail("m@gmail.com");
        clientUser.setPassword("MZMZMZMZMZMZZM");
        clientUser.setRole("ADMIN USER");
        clientUser.setEnable(true);

        MagasinDto magasinDto = new MagasinDto(
                null,
                "libekz",
                "description",
                this.logoMap,
                clientUser
        );


        Magasin magasin = new Magasin();
        magasin.setClientUser(clientUser);
        magasin.setDescription(magasinDto.description());
        magasin.setLibele(magasin.getLibele());
        magasin.setLogo(magasin.getLogo());
        //jso,Magasin
        String jsonmagasoin = objectMapper.writeValueAsString(magasinDto);

        //Given
        given(this.service.update(Mockito.any(Magasin.class),eq(1L))).willThrow(new ObjectNotFoundException("magasin",1L));

        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.patch(url+"/magasins/{magasindId}",1)
                        .content(jsonmagasoin)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Nous ne retrouvons pas l'entité magasin avec id 1"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    void deleteMagasinByIdSuccess() throws Exception {
        doNothing().when(this.service).deleteById(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete(url+"/magasins/{magasindId}",1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("magasin supprimé"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }
    @Test
    void deleteMagasinByIdNotFound() throws Exception {
        doThrow(new ObjectNotFoundException("magasin",1L)).when(this.service).deleteById(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete(url+"/magasins/{magasindId}",1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Nous ne retrouvons pas l'entité magasin avec id 1"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    void saveMagasin() throws Exception {
        ClientUser clientUser = new ClientUser();
        clientUser.setEmail("m@gmail.com");
        clientUser.setPassword("MZMZMZMZMZMZZM");
        clientUser.setRole("ADMIN USER");
        clientUser.setEnable(true);

        MagasinDto magasinDto = new MagasinDto(
                null,
                "libekz",
                "description",
                this.logoMap,
                clientUser
        );

        //jso,Magasin
        String jsonmagasoin = objectMapper.writeValueAsString(magasinDto);

        Magasin magasin = new Magasin();
        magasin.setClientUser(clientUser);
        magasin.setDescription(magasinDto.description());
        magasin.setLibele(magasin.getLibele());
        magasin.setLogo(magasin.getLogo());


        given(this.service.save(Mockito.any(Magasin.class))).willReturn(magasin);

        mockMvc.perform(MockMvcRequestBuilders.post(url+"/magasins")
                        .content(jsonmagasoin)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("magasin cré"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.libele").value(magasin.getLibele()))
                .andExpect(jsonPath("$.data.description").value(magasin.getDescription()))
                .andExpect(jsonPath("$.data.logo").value(magasin.getLogo()));

    }
}