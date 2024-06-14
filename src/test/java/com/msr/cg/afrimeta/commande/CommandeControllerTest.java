package com.msr.cg.afrimeta.commande;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msr.cg.afrimeta.clientUser.ClientUser;
import com.msr.cg.afrimeta.facture.Facture;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("dev")
class CommandeControllerTest {

    @MockBean
    CommandeService commandeService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${api.endpoint.base-url}")
    String url;

    List<Commande> commandes = new ArrayList<>();

    @BeforeEach
    void setUp() {
        ClientUser clientUser = new ClientUser("emmano","m@gmail.com","MZMZMZMZMZMZZM",true,"ADMIN USER",null);

        Commande commande1 = new Commande(LocalDateTime.now(),LocalDateTime.now(),22,"22 rue eboué",22,3,clientUser);

        Commande commande2 = new Commande(LocalDateTime.now(),LocalDateTime.now(),23,"23 rue eboué",22,2,clientUser);
        Commande commande3 = new Commande(LocalDateTime.now(),LocalDateTime.now(),24,"24 rue eboué",24,4,clientUser);
        Commande commande4 = new Commande(LocalDateTime.now(),LocalDateTime.now(),25,"22 rue eboué",25,5,clientUser);

        commandes.add(commande1);
        commandes.add(commande2);
        commandes.add(commande3);
        commandes.add(commande4);

    }

    @Test
    void getCommandes() throws Exception {
        //Given
        given(this.commandeService.findAll()).willReturn(this.commandes);
        List<Commande> actuallsLisCommande = this.commandeService.findAll();
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.get(url+"/commandes")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Toutes les commandes"))
                .andExpect(jsonPath("$.data", Matchers.notNullValue()))
                .andExpect(jsonPath("$.data.size()").value(actuallsLisCommande.size()))
        ;

    }

//    @Test
//    void save() {
//    }

   /* @Test
    void update() {
        ClientUser clientUser = new ClientUser("emmano","m@gmail.com","MZMZMZMZMZMZZM",true,"ADMIN USER",null);

        Commande commande1 = new Commande(LocalDateTime.now(),LocalDateTime.now(),22,"22 rue eboué",22,3,clientUser);

        //commande request
        //Commande

        //Given
        given(this.commandeService.update(Mockito.any(Commande.class),eq(1L))).willReturn(this.commandes.get(0));
        //When and Then

    }*/

    @Test
    void delete() {
    }
}