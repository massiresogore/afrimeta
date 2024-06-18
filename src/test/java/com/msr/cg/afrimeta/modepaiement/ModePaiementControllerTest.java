package com.msr.cg.afrimeta.modepaiement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msr.cg.afrimeta.modepaiement.dto.ModePaiementDto;
import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
//@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("dev")
class ModePaiementControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ModePaiementService modePaiementService;

    @Value("${api.endpoint.base-url}")
    String url;

    List<ModePaiement> modePaiementList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        ModePaiement modePaiement1 = new ModePaiement();
        modePaiement1.setNom("Aitel Money");

        ModePaiement modePaiement2 = new ModePaiement();
        modePaiement2.setNom("MTN Money");

        ModePaiement modePaiement3 = new ModePaiement();
        modePaiement3.setNom("Western Money");

        this.modePaiementList.add(modePaiement1);
        this.modePaiementList.add(modePaiement2);
        this.modePaiementList.add(modePaiement3);
    }

    @Test
    void getAllModePaiements() throws Exception {
        //Given
        given(this.modePaiementService.findAll()).willReturn(this.modePaiementList);
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.get(url+"/modepaiements")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("toutes les modepaiements"))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    void geModePaiementById() throws Exception {
        ModePaiement modePaiement1 = new ModePaiement();
        modePaiement1.setModePaiementId(1L);
        modePaiement1.setNom("Aitel Money");
        //Given
        given(this.modePaiementService.findById(1L)).willReturn(modePaiement1);
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.get(url+"/modepaiements/{modepaiementId}",1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("modePaiement retrouvée"))
                .andExpect(jsonPath("$.data.nom").value("Aitel Money"));
    }

    @Test
    void geModePaiementByIdNotFound() throws Exception {

        //Given
        doThrow(new ObjectNotFoundException(ModePaiement.class.getSimpleName(),10L)).when(this.modePaiementService).findById(1L);
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.get(url+"/modepaiements/{modepaiementId}",1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Nous ne retrouvons pas l'entité ModePaiement avec id 10"))
                .andExpect(jsonPath("$.data", Matchers.nullValue()));
    }

    @Test
    void updateModePaiementSuccess() throws Exception {
        ModePaiement modePaiement1 = new ModePaiement();
        modePaiement1.setModePaiementId(1L);
        modePaiement1.setNom("Aitel Money cooll");

        ModePaiementDto modePaiementDto = new ModePaiementDto(1L,"France");
        String json = objectMapper.writeValueAsString(modePaiementDto);
        //Given
        given(this.modePaiementService.update(Mockito.any(ModePaiement.class),eq(1L))).willReturn(modePaiement1);
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.patch(url+"/modepaiements/{modepaiementId}",1)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("modePaiement mis a jour"))
                .andExpect(jsonPath("$.data.nom").value("Aitel Money cooll"));

    }

    @Test
    void updateModePaiementNotFound() throws Exception {

        ModePaiementDto modePaiementDto = new ModePaiementDto(1L,"France");
        String json = objectMapper.writeValueAsString(modePaiementDto);
        //Given
        doThrow(new ObjectNotFoundException(ModePaiement.class.getSimpleName(),10L)).when(this.modePaiementService).update(Mockito.any(ModePaiement.class),eq(1L));
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.patch(url+"/modepaiements/{modepaiementId}",1)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Nous ne retrouvons pas l'entité ModePaiement avec id 10"))
                .andExpect(jsonPath("$.data", Matchers.nullValue()));

    }

    @Test
    void deleteModePaiementSuccess() throws Exception {
        //Given
        doNothing().when(this.modePaiementService).deleteById(1L);
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.delete(url+"/modepaiements/{modepaiementId}",1)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("modePaiement supprimée"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void deleteModePaiementNotFound() throws Exception {
        //Given
        doThrow(new ObjectNotFoundException(ModePaiement.class.getSimpleName(),10L)).when(this.modePaiementService).deleteById(10L);

        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.delete(url+"/modepaiements/{modepaiementId}",10)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Nous ne retrouvons pas l'entité ModePaiement avec id 10"))
                .andExpect(jsonPath("$.data", Matchers.nullValue()));

    }

    @Test
    void saveModePaiement() throws Exception {
        ModePaiement modePaiement1 = new ModePaiement();
        modePaiement1.setNom("Aitel Money cooll");

        ModePaiementDto modePaiementDto = new ModePaiementDto(null,"France");
        String json = objectMapper.writeValueAsString(modePaiementDto);
        //Given
        given(this.modePaiementService.save(Mockito.any(ModePaiement.class))).willReturn(modePaiement1);
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.post(url+"/modepaiements")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("modePaiement crée"))
                .andExpect(jsonPath("$.data.nom").value("Aitel Money cooll"));
    }
}