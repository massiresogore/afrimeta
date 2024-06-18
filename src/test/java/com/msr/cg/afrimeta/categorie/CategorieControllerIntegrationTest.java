package com.msr.cg.afrimeta.categorie;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msr.cg.afrimeta.categorie.dto.CategorieDto;
import com.msr.cg.afrimeta.system.StatusCode;
import org.apache.coyote.Request;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

@SpringBootTest
@AutoConfigureMockMvc
@Tag("integration")
@ActiveProfiles("dev")
public class CategorieControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Value("${api.endpoint.base-url}")
    String url;

    String token;

    @Autowired
    ObjectMapper objectMapper;

   /* @BeforeEach
    void setUpt() throws Exception //sera appellé avant chaque test
    {
        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post(this.baseUrl + "/auth/login").with( httpBasic("Mampo", "123456789"))); // httpBasic() is from spring-security-test.
        MvcResult mvcResult = resultActions.andDo(print()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        JSONObject json = new JSONObject(contentAsString);
        this.token = "Bearer " + json.getJSONObject("data").getString("token"); // Don't forget to add "Bearer " as prefix.
    }*/

    @Test
//    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
        //Reinitialise la base de donnée avant d'exécuter cette méthode
    void findAllCategorieSuccess() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(url+"/categories")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("toutes les categories"))
                .andExpect(jsonPath("$.data", Matchers.hasSize(5)));
    }
/*
    @Test
    void saveCategorie() throws Exception {
        //Dto
        CategorieDto categorieDto = new CategorieDto(1L,"Dior");
        //object
        Categorie categorie = new Categorie(categorieDto.categorieId(),categorieDto.nom());
        //object to string
        String jsonCategorie = objectMapper.writeValueAsString(categorie);
        mockMvc.perform(MockMvcRequestBuilders.post(url+"/categories")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonCategorie)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("categorie crée"))
                .andExpect(jsonPath("$.data.categorieId").value(1))
                .andExpect(jsonPath("$.data.nom").value("Dior"));
        this.mockMvc.perform(MockMvcRequestBuilders.get(url+"/categories")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("toutes les categories"))
                .andExpect(jsonPath("$.data", Matchers.hasSize(6)));
    }*/
}
