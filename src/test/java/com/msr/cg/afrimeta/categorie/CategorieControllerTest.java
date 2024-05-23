package com.msr.cg.afrimeta.categorie;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)//Désactive la sécurité spring boot
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
        Categorie c2 = new Categorie(1L,"Nike");
        Categorie c3 = new Categorie(1L,"Adidas");
        Categorie c4 = new Categorie(1L,"Hermes");
        Categorie c5 = new Categorie(1L,"Mosh");
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
                .andExpect(jsonPath("$.message").value("toutes les catégorie"))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].name").value("Dior"))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].name").value("Nike"))
        ;

    }

    @Test
    void geCategorieById() {
    }

    @Test
    void updateCategorie() {
    }

    @Test
    void deleteCategorie() {
    }

    @Test
    void saveCategorie() {
    }
}