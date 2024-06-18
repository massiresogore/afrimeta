package com.msr.cg.afrimeta.paiement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msr.cg.afrimeta.clientUser.ClientUser;
import com.msr.cg.afrimeta.commande.Commande;
import com.msr.cg.afrimeta.modepaiement.ModePaiement;
import com.msr.cg.afrimeta.paiement.converter.PaiementToPaiementDtoConverter;
import com.msr.cg.afrimeta.paiement.dto.PaiementDto;
import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
//@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("dev")
class PaiementControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PaiementService paiementService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${api.endpoint.base-url}")
    String url;

    List<Paiement> paiements = new ArrayList<>();

    @Autowired
    PaiementToPaiementDtoConverter paiementToPaiementDtoConverter;

    @BeforeEach
    void setUp() {
        //Mode paiement
        ModePaiement modePaiement1 = new ModePaiement();
        modePaiement1.setNom("Aitel Money");
        //ClientUser
        ClientUser clientUser = new ClientUser("emmano","m@gmail.com",null,true,"ADMIN USER",null);

        clientUser.setPassword(passwordEncoder.encode("password"));
        Commande commande1 = new Commande(1L, LocalDateTime.now(),LocalDateTime.now(),22,"33 rue bandas",22,7,clientUser);
        //Maiement
        Paiement paiement1 = new Paiement(1L, LocalDateTime.now(),"description1",modePaiement1,commande1);

        Paiement paiement2 = new Paiement(2L, LocalDateTime.now(),"description2",modePaiement1,commande1);
        Paiement paiement3 = new Paiement(3L, LocalDateTime.now(),"description3",modePaiement1,commande1);
        paiements.add(paiement1);
        paiements.add(paiement2);
        paiements.add(paiement3);
    }

    @Test
    void getAll() throws Exception {
        //Given
        given(this.paiementService.findAll()).willReturn(paiements);
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.get(url+"/paiements")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true));
    }

    @Test
    void getByIdSuccess() throws Exception {
        //Given
        given(this.paiementService.findById(1L)).willReturn(paiements.get(0));
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.get(url+"/paiements/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.data.description").value("description1"));
    }
    @Test
    void getByIdNotFound() throws Exception {
        //Given
        given(this.paiementService.findById(1L)).willThrow(ObjectNotFoundException.class);

        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.get(url+"/paiements/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.data", Matchers.nullValue()));
    }
/*

    @Test
    void update() throws Exception {
        //Mode paiement
        ModePaiement modePaiement1 = new ModePaiement();
        modePaiement1.setNom("Aitel Money");
        //ClientUser
        ClientUser clientUser = new ClientUser("emmano","m@gmail.com","MZMZMZMZMZMZZM",true,"ADMIN USER",null);
        Commande commande1 = new Commande(1L, LocalDateTime.now(),LocalDateTime.now(),22,"33 rue bandas",22,7,clientUser);
        //Maiement
        Paiement paiement1 = new Paiement(1L, LocalDateTime.now(),"description1",modePaiement1,commande1);

        String paiementJson = objectMapper.writeValueAsString(paiement1);
        //Given
        given(this.paiementService.update(paiement1,1L)).willReturn(paiement1);

        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.patch(url+"/paiements/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(paiementJson)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.data", Matchers.notNullValue()));
    }
*/

    @Test
    void delete() throws Exception {
        doNothing().when(paiementService).deleteById(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete(url+"/paiements/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("paiement annulé"))
                .andExpect(jsonPath("$.data",Matchers.nullValue()));
    }

    @Test
    void deleteByIdNotFound() throws Exception {
        doThrow(new ObjectNotFoundException(Paiement.class.getSimpleName(),112L)).when(paiementService).deleteById(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete(url+"/paiements/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Nous ne retrouvons pas l'entité Paiement avec id 112"))
                .andExpect(jsonPath("$.data",Matchers.nullValue()));
    }
}