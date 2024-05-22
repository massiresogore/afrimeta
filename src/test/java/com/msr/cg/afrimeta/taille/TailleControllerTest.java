package com.msr.cg.afrimeta.taille;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import com.msr.cg.afrimeta.taille.dto.TailleDto;
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

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("dev")
class TailleControllerTest {

    @MockBean
    TailleService tailleService;

    @Autowired
    MockMvc mockMvc;

    @Value("${api.endpoint.base-url}")
    String url;

    @Autowired
    ObjectMapper mapper;

    List<Taille> tailles = new ArrayList<>();
    @BeforeEach
    void setUp() {
        Taille taille1 = new Taille(1L,"Mango");
        Taille taillet2 = new Taille(2L,"Fruit");
        Taille taillet3 = new Taille(3L,"Banana");
        Taille taillet4 = new Taille(4L,"Apple");
        Taille taillet5 = new Taille(5L,"Orange");

        tailles.add(taille1);
        tailles.add(taillet2);
        tailles.add(taillet3);
        tailles.add(taillet4);
        tailles.add(taillet5);
    }

    @Test
    void getAllTailles() throws Exception {
        //Given
        given(this.tailleService.findAll()).willReturn(tailles);
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.get(url+"/taille")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("toutes les tailles"))
                .andExpect(jsonPath("$.data.size()").value(this.tailles.size()))
                .andExpect(jsonPath("$.data[0].nom").value(this.tailles.get(0).getNom()));
    }

    @Test
    void getTailleById() throws Exception {
        Taille taille = new Taille(1L,"Mango");

        //Given
        given(this.tailleService.findById(1L)).willReturn(taille);
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.get(url+"/taille/{tailleId}",1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("taille retrouvée"))
                .andExpect(jsonPath("$.data.nom").value(this.tailles.get(0).getNom()));
    }  @Test
    void getTailleByIdNotFound() throws Exception {
        Taille taille = new Taille(12L,"Mango");

        //Given
        given(this.tailleService.findById(12L)).willThrow(new ObjectNotFoundException(taille.getClass().getSimpleName(),12L));
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.get(url+"/taille/{tailleId}",12L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Nous ne retrouvons pas l'entité Taille avec id 12"))
                .andExpect(jsonPath("$.data", Matchers.nullValue()));
    }

    @Test
    void updateTaille() throws Exception {
        //Dto
        TailleDto tailleDto = new TailleDto(12L,"Mango");
        // dto to json
        String tailleJson = mapper.writeValueAsString(tailleDto);
        //Objec
        Taille taille = new Taille(tailleDto.tailleId(),tailleDto.nom());
        //Given
        given(this.tailleService.update(Mockito.any(Taille.class),eq(12L))).willReturn(tailles.get(0));
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.patch(url+"/taille/{tailleId}",12)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tailleJson))
         .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("taille mis a jour"))
                .andExpect(jsonPath("$.data.nom").value(taille.getNom()));
    }

    @Test
    void updateTailleNotFound() throws Exception {
        //Dto
        TailleDto tailleDto = new TailleDto(12L,"Mango");
        // dto to json
        String tailleJson = mapper.writeValueAsString(tailleDto);
        //Objec
        Taille taille = new Taille(tailleDto.tailleId(),tailleDto.nom());
        //Given
        given(this.tailleService.update(Mockito.any(Taille.class),eq(12L))).willThrow(new ObjectNotFoundException(taille.getClass().getSimpleName(),12L));
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.patch(url+"/taille/{tailleId}",12)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tailleJson))
         .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Nous ne retrouvons pas l'entité Taille avec id 12"))
                .andExpect(jsonPath("$.data",Matchers.nullValue()));
    }

    @Test
    void deleteTailleById() throws Exception {
        doNothing().when(this.tailleService).deleteById(12L);
        mockMvc.perform(MockMvcRequestBuilders.delete(url+"/taille/{tailleId}",12)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("taille supprimée"))
                .andExpect(jsonPath("$.data",Matchers.nullValue()));

    }

    @Test
    void deleteTailleByIdNotFound() throws Exception {
        doThrow(new ObjectNotFoundException(Taille.class.getSimpleName(),12L)).when(this.tailleService).deleteById(12L);
        mockMvc.perform(MockMvcRequestBuilders.delete(url+"/taille/{tailleId}",12)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Nous ne retrouvons pas l'entité Taille avec id 12"))
                .andExpect(jsonPath("$.data",Matchers.nullValue()));

    }

    @Test
    void saveTaille() throws Exception {
        //Dto
        TailleDto tailleDto = new TailleDto(null,"Mango");
        // dto to json
        String tailleJson = mapper.writeValueAsString(tailleDto);
        //Objec
        Taille taille = new Taille(tailleDto.tailleId(),tailleDto.nom());
        //Given
        given(this.tailleService.save(Mockito.any(Taille.class))).willReturn(taille);
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.post(url+"/taille")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tailleJson))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("taille crée"))
                .andExpect(jsonPath("$.data.nom").value(taille.getNom()));
    }
}