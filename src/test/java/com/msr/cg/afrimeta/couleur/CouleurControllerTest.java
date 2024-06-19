package com.msr.cg.afrimeta.couleur;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msr.cg.afrimeta.couleur.dto.CouleurDto;
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
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import  static  org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)//désactive la securité
@ActiveProfiles("dev")
class CouleurControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    CouleurService couleurService;

    @Value("${api.endpoint.base-url}")
    String url;

    @Autowired
    ObjectMapper objectMapper;

    List<Couleur> couleurs = new ArrayList<>();
    @BeforeEach
    void setUp() {
        Couleur couleur1 = new Couleur("Yellow");
        Couleur couleur2 = new Couleur("Red");
        Couleur couleur3 = new Couleur("Green");
        Couleur couleur4 = new Couleur("Blue");
        Couleur couleur5 = new Couleur("Yellow");
        couleurs.add(couleur1);
        couleurs.add(couleur2);
        couleurs.add(couleur3);
        couleurs.add(couleur4);
        couleurs.add(couleur5);
    }


    @Test
    void getAllCouleurs() throws Exception {
        //Given
        given(this.couleurService.findAll()).willReturn(couleurs);
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.get(url+"/couleurs")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("toutes les couleurs"))
                .andExpect(jsonPath("$.data", Matchers.hasSize(5)));
    }

    @Test
    void geCouleurById() throws Exception {
        Couleur couleur = new Couleur(1L,"Yellow");

        //Given
        given(this.couleurService.findById(1L)).willReturn(couleur);
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.get(url+"/couleurs/{couleurId}",1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("couleur retrouvée"))
                .andExpect(jsonPath("$.data.couleurId").value(1L))
                .andExpect(jsonPath("$.data.nom").value("Yellow"));
    }

    @Test
    void geCouleurByIdNotFound() throws Exception {
        //Given
        given(this.couleurService.findById(18L)).willThrow(new ObjectNotFoundException(Couleur.class.getSimpleName(),18L));
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.get(url+"/couleurs/{couleurId}",18L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Nous ne retrouvons pas l'entité Couleur avec id 18"))
                .andExpect(jsonPath("$.data",Matchers.nullValue()));
    }

    @Test
    void updateCouleurById() throws Exception {
        //Dto
        CouleurDto couleurDto = new CouleurDto(1L,"Yellow");
        //Dto to object
        Couleur couleur = new Couleur(couleurDto.couleurId(),couleurDto.nom());
        // obecj to string
        String jsonCouleur = objectMapper.writeValueAsString(couleur);
        //Given
        given(this.couleurService.update(Mockito.any(Couleur.class),eq(1L))).willReturn(couleur);
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.patch(url+"/couleurs/{couleurId}",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCouleur)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("couleur mis a jour"))
                .andExpect(jsonPath("$.data.couleurId").value(1L))
                .andExpect(jsonPath("$.data.nom").value("Yellow"));

    }

    @Test
    void updateCouleurByIdNotFound() throws Exception {
        //Dto
        CouleurDto couleurDto = new CouleurDto(1L,"Yellow");
        //Dto to object
        Couleur couleur = new Couleur(couleurDto.couleurId(),couleurDto.nom());
        // obecj to string
        String jsonCouleur = objectMapper.writeValueAsString(couleur);
        //Given
        given(this.couleurService.update(Mockito.any(Couleur.class),eq(1L))).willThrow(new ObjectNotFoundException(Couleur.class.getSimpleName(),1L));

        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.patch(url+"/couleurs/{couleurId}",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCouleur)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Nous ne retrouvons pas l'entité Couleur avec id 1"))
                .andExpect(jsonPath("$.data",Matchers.nullValue()));
    }

    @Test
    void deleteCouleurById() throws Exception {
        doNothing().when(this.couleurService).deleteById(1L);
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.delete(url+"/couleurs/{couleurId}",1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("couleur supprimée"))
                .andExpect(jsonPath("$.data",Matchers.nullValue()));
    }
    @Test
    void deleteCouleurByIdNotFound() throws Exception {
        doThrow(new ObjectNotFoundException(Couleur.class.getSimpleName(),1L)).when(this.couleurService).deleteById(1L);
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.delete(url+"/couleurs/{couleurId}",1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Nous ne retrouvons pas l'entité Couleur avec id 1"))
                .andExpect(jsonPath("$.data",Matchers.nullValue()));
    }

    @Test
    void saveCouleur() throws Exception {
        //Dto
        CouleurDto couleurDto = new CouleurDto(1L,"Yellow");
        //Dto to object
        Couleur couleur = new Couleur(couleurDto.couleurId(),couleurDto.nom());
        // obecj to string
        String jsonCouleur = objectMapper.writeValueAsString(couleur);
        //Given
        given(this.couleurService.save(Mockito.any(Couleur.class))).willReturn(couleur);
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.post(url+"/couleurs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCouleur)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("couleur crée"))
                .andExpect(jsonPath("$.data.couleurId").value(1L))
                .andExpect(jsonPath("$.data.nom").value("Yellow"));

    }
}