package com.msr.cg.afrimeta.typeproduit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import com.msr.cg.afrimeta.typeproduit.dto.TypeProduitDto;
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

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("dev")
class TypeProduitControllerTest {
    @MockBean
    TypeProduitService typeProduitService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${api.endpoint.base-url}")
    String url;

    List<TypeProduit> typeProduits = new ArrayList<>();

    @BeforeEach
    void setUp() {
        TypeProduit typeProduit1 = new TypeProduit(1L,"Mango");
        TypeProduit typeProduit2 = new TypeProduit(2L,"Fruit");
        TypeProduit typeProduit3 = new TypeProduit(3L,"Banana");
        TypeProduit typeProduit4 = new TypeProduit(4L,"Apple");
        TypeProduit typeProduit5 = new TypeProduit(5L,"Orange");

        typeProduits.add(typeProduit1);
        typeProduits.add(typeProduit2);
        typeProduits.add(typeProduit3);
        typeProduits.add(typeProduit4);
        typeProduits.add(typeProduit5);
    }

    @Test
    void getAllTypeProduits() throws Exception {
        //Given
        given(this.typeProduitService.findAll()).willReturn(typeProduits);
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.get(url+"/type-produit")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("tous les type de produits"))
                .andExpect(jsonPath("$.data[0].typeProduitId").value(this.typeProduits.get(0).getTypeProduitId()))
                .andExpect(jsonPath("$.data[0].nom").value(this.typeProduits.get(0).getNom()));
    }

    @Test
    void getTypeProduitIdSuccess() throws Exception {
        TypeProduit typeProduit = new TypeProduit(1L,"Mango");
        given(this.typeProduitService.findById(1L)).willReturn(typeProduit);
        mockMvc.perform(MockMvcRequestBuilders.get(url+"/type-produit/{typeProduitId}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("type de produit retrouvé"))
                .andExpect(jsonPath("$.data.typeProduitId").value(typeProduit.getTypeProduitId()))
                .andExpect(jsonPath("$.data.nom").value(typeProduit.getNom()));
    }

    @Test
    void getTypeProduitByIdNotFound() throws Exception {
        given(this.typeProduitService.findById(1L)).willThrow(new ObjectNotFoundException("TypeProduit",1L));
        mockMvc.perform(MockMvcRequestBuilders.get(url+"/type-produit/{typeProduitId}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Nous ne retrouvons pas l'entité TypeProduit avec id 1"))
                .andExpect(jsonPath("$.data", Matchers.nullValue()));
    }

    @Test
    void updateTypeProduit() throws Exception {
        //Dto
        TypeProduitDto typeProduitDto = new TypeProduitDto(1L,"Mango");
        //dto to json string
        String typeProduitJson = objectMapper.writeValueAsString(typeProduitDto);
        //object
        TypeProduit typeProduit = new TypeProduit(typeProduitDto.nom());
        //Given
        given(this.typeProduitService.update(Mockito.any(TypeProduit.class),eq(1L))).willReturn(typeProduit);
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.patch(url+"/type-produit/{typeProduitId}", 1L)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(typeProduitJson))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("type de produit mis a jour"))
                .andExpect(jsonPath("$.data.typeProduitId").value(typeProduit.getTypeProduitId()))
                .andExpect(jsonPath("$.data.nom").value(typeProduit.getNom()));
    }

    @Test
    void updateTypeProduitNotFound() throws Exception {
        //Dto
        TypeProduitDto typeProduitDto = new TypeProduitDto(1L,"Mango");
        //dto to json string
        String typeProduitJson = objectMapper.writeValueAsString(typeProduitDto);
        //object
        TypeProduit typeProduit = new TypeProduit(typeProduitDto.nom());
        //Given
        given(this.typeProduitService.update(Mockito.any(TypeProduit.class),eq(1L))).willThrow(new ObjectNotFoundException("TypeProduit",1L));
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.patch(url+"/type-produit/{typeProduitId}", 1L)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(typeProduitJson))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Nous ne retrouvons pas l'entité TypeProduit avec id 1"))
                .andExpect(jsonPath("$.data", Matchers.nullValue()));
    }

    @Test
    void deleteTypeProduit() throws Exception {
        //Given
        doNothing().when(this.typeProduitService).deleteById(1L);
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.delete(url+"/type-produit/{typeProduitId}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("type produit supprimé"))
                .andExpect(jsonPath("$.data",Matchers.nullValue()));
    }

    @Test
    void deleteTypeProduitNotFound() throws Exception {
        doThrow(new ObjectNotFoundException("TypeProduit",1L)).when(this.typeProduitService).deleteById(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete(url+"/type-produit/{typeProduitId}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Nous ne retrouvons pas l'entité TypeProduit avec id 1"))
                .andExpect(jsonPath("$.data", Matchers.nullValue()));
    }

    @Test
    void saveTypeProduit() throws Exception {
        //Dto
        TypeProduitDto typeProduitDto = new TypeProduitDto(null,"Mango");
        //dto to json string
        String typeProduitJson = objectMapper.writeValueAsString(typeProduitDto);
        //object
        TypeProduit typeProduit = new TypeProduit();
        typeProduit.setNom(typeProduitDto.nom());
        //Given
        given(this.typeProduitService.save(Mockito.any(TypeProduit.class))).willReturn(typeProduit);
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.post(url+"/type-produit")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(typeProduitJson))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("type de produit crée"))
                .andExpect(jsonPath("$.data.nom").value(typeProduit.getNom()));

    }
}