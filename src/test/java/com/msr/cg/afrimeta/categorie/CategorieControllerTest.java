package com.msr.cg.afrimeta.categorie;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msr.cg.afrimeta.categorie.dto.CategorieDto;
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

import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
//@AutoConfigureMockMvc(addFilters = false)//Désactive la sécurité spring boot
@ActiveProfiles("dev")
class CategorieControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CategorieService categorieService;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${api.endpoint.base-url}")
    String url;

    List<Categorie> categories = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Categorie c1 = new Categorie(1L,"Dior");
        Categorie c2 = new Categorie(2L,"Nike");
        Categorie c3 = new Categorie(3L,"Adidas");
        Categorie c4 = new Categorie(4L,"Hermes");
        Categorie c5 = new Categorie(5L,"Mosh");
        categories.add(c1);
        categories.add(c2);
        categories.add(c3);
        categories.add(c4);
        categories.add(c5);
    }


    @Test
    void getAllCategories() throws Exception {
        //Given
        given(this.categorieService.findAll()).willReturn(categories);
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.get(url+"/categories")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("toutes les categories"))
                .andExpect(jsonPath("$.data[0].categorieId").value(1))
                .andExpect(jsonPath("$.data[0].nom").value("Dior"))
                .andExpect(jsonPath("$.data[1].categorieId").value(2))
                .andExpect(jsonPath("$.data[1].nom").value("Nike"))
        ;

    }

    @Test
    void geCategorieById() throws Exception {
        given(this.categorieService.findById(1L)).willReturn(categories.get(0));
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.get(url+"/categories/{catehorieId}",1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("categorie retrouvée"))
                .andExpect(jsonPath("$.data.categorieId").value(1))
                .andExpect(jsonPath("$.data.nom").value("Dior"));
    }

    @Test
    void geCategorieByIdNotFound() throws Exception {
        given(this.categorieService.findById(1L)).willThrow(new ObjectNotFoundException(Categorie.class.getSimpleName(),1L));
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.get(url+"/categories/{catehorieId}",1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Nous ne retrouvons pas l'entité Categorie avec id 1"))
                .andExpect(jsonPath("$.data", Matchers.nullValue()));
    }

    @Test
    void updateCategorie() throws Exception {
        //Dto
        CategorieDto categorieDto = new CategorieDto(1L,"Dior");
        //object
        Categorie categorie = new Categorie(categorieDto.categorieId(),categorieDto.nom());
        //object to string
        String jsonCategorie = objectMapper.writeValueAsString(categorie);
        //given
        given(this.categorieService.update(Mockito.any(Categorie.class),eq(1L))).willReturn(categorie);
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.patch(url+"/categories/{categorieId}",1L)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonCategorie)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("categorie mis a jour"))
                .andExpect(jsonPath("$.data.categorieId").value(1))
                .andExpect(jsonPath("$.data.nom").value("Dior"));
    }

    @Test
    void updateCategorieNotFound() throws Exception {
        //Dto
        CategorieDto categorieDto = new CategorieDto(1L,"Dior");
        //object
        Categorie categorie = new Categorie(categorieDto.categorieId(),categorieDto.nom());
        //object to string
        String jsonCategorie = objectMapper.writeValueAsString(categorie);
        //given
        given(this.categorieService.update(Mockito.any(Categorie.class),eq(1L))).willThrow(new ObjectNotFoundException(Categorie.class.getSimpleName(),1L));
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.patch(url+"/categories/{categorieId}",1L)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonCategorie)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Nous ne retrouvons pas l'entité Categorie avec id 1"))
                .andExpect(jsonPath("$.data", Matchers.nullValue()));
    }

    @Test
    void deleteCategorieById() throws Exception {
        //Given
        doNothing().when(this.categorieService).deleteById(1L);
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.delete(url+"/categories/{categorieId}",1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("categorie supprimée"))
                .andExpect(jsonPath("$.data",Matchers.nullValue()));
    }

    @Test
    void deleteCategorieByIdNotFound() throws Exception {
        //Given
        doThrow(new ObjectNotFoundException(Categorie.class.getSimpleName(),1L)).when(this.categorieService).deleteById(1L);
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.delete(url+"/categories/{categorieId}",1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Nous ne retrouvons pas l'entité Categorie avec id 1"))
                .andExpect(jsonPath("$.data", Matchers.nullValue()));
    }

    @Test
    void saveCategorie() throws Exception {
        //Dto
        CategorieDto categorieDto = new CategorieDto(1L,"Dior");
        //object
        Categorie categorie = new Categorie(categorieDto.categorieId(),categorieDto.nom());
        //object to string
        String jsonCategorie = objectMapper.writeValueAsString(categorie);
        //given
        given(this.categorieService.save(Mockito.any(Categorie.class))).willReturn(categorie);
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.post(url+"/categories")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonCategorie)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("categorie crée"))
                .andExpect(jsonPath("$.data.categorieId").value(1))
                .andExpect(jsonPath("$.data.nom").value("Dior"));
    }
}