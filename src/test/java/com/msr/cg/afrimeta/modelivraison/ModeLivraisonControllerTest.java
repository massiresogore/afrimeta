package com.msr.cg.afrimeta.modelivraison;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msr.cg.afrimeta.modelivraison.dto.ModeLivraisonDto;
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

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
//@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("dev")
class ModeLivraisonControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ModeLivraisonService  modeLivraisonService;

    @Value("${api.endpoint.base-url}")
    String url;

    List<ModeLivraison> modeLivraisonList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        ModeLivraison modeLivraison1 = new ModeLivraison();
        modeLivraison1.setNom("Aitel Money");

        ModeLivraison modeLivraison2 = new ModeLivraison();
        modeLivraison2.setNom("MTN Money");

        ModeLivraison modeLivraison3 = new ModeLivraison();
        modeLivraison3.setNom("Western Money");

        this.modeLivraisonList.add(modeLivraison1);
        this.modeLivraisonList.add(modeLivraison2);
        this.modeLivraisonList.add(modeLivraison3);
    }

    @Test
    void getAllModeLivraisons() throws Exception {
        //Given
        given(this.modeLivraisonService.findAll()).willReturn(this.modeLivraisonList);
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.get(url+"/modelivraisons")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("toutes les modelivraisons"))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    void geModeLivraisonById() throws Exception {
        ModeLivraison modeLivraison1 = new ModeLivraison();
        modeLivraison1.setModeLivraisonId(1L);
        modeLivraison1.setNom("Aitel Money");
        //Given
        given(this.modeLivraisonService.findById(1L)).willReturn(modeLivraison1);
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.get(url+"/modelivraisons/{modelivraisonId}",1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("modeLivraison retrouvée"))
                .andExpect(jsonPath("$.data.nom").value("Aitel Money"));
    }

    @Test
    void geModeLivraisonByIdNotFound() throws Exception {

        //Given
        doThrow(new ObjectNotFoundException(ModeLivraison.class.getSimpleName(),10L)).when(this.modeLivraisonService).findById(1L);
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.get(url+"/modelivraisons/{modelivraisonId}",1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Nous ne retrouvons pas l'entité ModeLivraison avec id 10"))
                .andExpect(jsonPath("$.data", Matchers.nullValue()));
    }

    @Test
    void updateModeLivraisonSuccess() throws Exception {
        ModeLivraison modeLivraison1 = new ModeLivraison();
        modeLivraison1.setModeLivraisonId(1L);
        modeLivraison1.setNom("Aitel Money cooll");

        ModeLivraisonDto modeLivraisonDto = new ModeLivraisonDto(1L,"France");
        String json = objectMapper.writeValueAsString(modeLivraisonDto);
        //Given
        given(this.modeLivraisonService.update(Mockito.any(ModeLivraison.class),eq(1L))).willReturn(modeLivraison1);
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.patch(url+"/modelivraisons/{modelivraisonId}",1)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("modeLivraison mis a jour"))
                .andExpect(jsonPath("$.data.nom").value("Aitel Money cooll"));

    }

    @Test
    void updateModeLivraisonNotFound() throws Exception {

        ModeLivraisonDto modeLivraisonDto = new ModeLivraisonDto(1L,"France");
        String json = objectMapper.writeValueAsString(modeLivraisonDto);
        //Given
        doThrow(new ObjectNotFoundException(ModeLivraison.class.getSimpleName(),10L)).when(this.modeLivraisonService).update(Mockito.any(ModeLivraison.class),eq(1L));
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.patch(url+"/modelivraisons/{modelivraisonId}",1)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Nous ne retrouvons pas l'entité ModeLivraison avec id 10"))
                .andExpect(jsonPath("$.data", Matchers.nullValue()));

    }

    @Test
    void deleteModeLivraisonSuccess() throws Exception {
        //Given
        doNothing().when(this.modeLivraisonService).deleteById(1L);
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.delete(url+"/modelivraisons/{modelivraisonId}",1)
                .accept(MediaType.APPLICATION_JSON))

        .andExpect(jsonPath("$.flag").value(true))
        .andExpect(jsonPath("$.code").value(200))
        .andExpect(jsonPath("$.message").value("modeLivraison supprimée"))
        .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void deleteModeLivraisonNotFound() throws Exception {
        //Given
        doThrow(new ObjectNotFoundException(ModeLivraison.class.getSimpleName(),10L)).when(this.modeLivraisonService).deleteById(10L);

        //When and Then
            mockMvc.perform(MockMvcRequestBuilders.delete(url+"/modelivraisons/{modelivraisonId}",10)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.flag").value(false))
                    .andExpect(jsonPath("$.code").value(404))
                    .andExpect(jsonPath("$.message").value("Nous ne retrouvons pas l'entité ModeLivraison avec id 10"))
                    .andExpect(jsonPath("$.data", Matchers.nullValue()));

    }

    @Test
    void saveModeLivraison() throws Exception {
        ModeLivraison modeLivraison1 = new ModeLivraison();
        modeLivraison1.setNom("Aitel Money cooll");

        ModeLivraisonDto modeLivraisonDto = new ModeLivraisonDto(null,"France");
        String json = objectMapper.writeValueAsString(modeLivraisonDto);
        //Given
        given(this.modeLivraisonService.save(Mockito.any(ModeLivraison.class))).willReturn(modeLivraison1);
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.post(url+"/modelivraisons")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("modeLivraison crée"))
                .andExpect(jsonPath("$.data.nom").value("Aitel Money cooll"));
    }
}