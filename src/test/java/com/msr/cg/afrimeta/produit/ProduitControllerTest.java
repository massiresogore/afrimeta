package com.msr.cg.afrimeta.produit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msr.cg.afrimeta.produit.dto.dto.ProduitDto;
import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import com.msr.cg.afrimeta.website.Website;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("dev")
class ProduitControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProduitService produitService;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${api.endpoint.base-url}")
    String url;

    List<Produit> produits = new ArrayList<>();

    @BeforeEach
    void setUp() {

        Website website1 = new Website(1L,"http://google.com",null);
        Website website2 = new Website(2L,"http://google.com",null);
        Website website3 = new Website(3L,"http://google.com",null);
        Website website4 = new Website(4L,"http://google.com",null);
        Website website5 = new Website(5L,"http://google.com",null);

        Produit produit1 = new Produit(null, "Apple iPhone 14", "Latest model with advanced features", 50,  999.99, null, null, null, website1);
        Produit produit2 = new Produit(null, "Samsung Galaxy S21", "High performance and sleek design", 30,  799.99, null, null, null, website2);
        Produit produit3 = new Produit(null, "Sony WH-1000XM4", "Noise cancelling wireless headphones", 100,  349.99, null, null, null, website3);
        Produit produit4 = new Produit(null, "Dell XPS 13", "Compact and powerful laptop", 20,  1299.99, null, null, null, website3);
        Produit produit5 = new Produit(null, "Nintendo Switch", "Versatile gaming console", 70, 299.99, null, null, null, website5);

        produits.add(produit1);
        produits.add(produit2);
        produits.add(produit3);
        produits.add(produit4);
        produits.add(produit5);
    }

   /* @Test
    void getAll() throws Exception {
        //given
        given(this.produitService.selectProduitByWebsiteId("3")).willReturn(produits);
        //when and Then
        mockMvc.perform(MockMvcRequestBuilders.get(url+"/produits/website/{websiteId}",3)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("tous les produits de website avec leur catégorie et type"))
                .andExpect(jsonPath("$.data", Matchers.hasSize(5)));
    }*/

    @Test
    void showSuccess() throws Exception {
        Website website = new Website(1L,"http://google.com",null);
        Produit produit = new Produit(1L, "Apple iPhone 14", "Latest model with advanced features", 50, 999.99, null, null, null, website);

        //given
        given(this.produitService.singleProduitByProduitId("1")).willReturn(produit);
        //When and then
        mockMvc.perform(MockMvcRequestBuilders.get(url+"/produits/{produitsId}",1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("produit retrouvé"))
                .andExpect(jsonPath("$.data",Matchers.notNullValue()))
                .andExpect(jsonPath("$.data.description").value("Latest model with advanced features"))
                .andExpect(jsonPath("$.data.quantiteStock").value(50));

    }


    /*@Test
    void store() throws Exception {
        // dto
        Website website1 = new Website(1L,"http://google.com",null);
        ProduitDto produitDto = new ProduitDto(null, "Apple iPhone 14", "Latest model with advanced features", 50, "http://example.com/image1.jpg", 999.99, null, null, null, website1,null);

        String jsonDto = objectMapper.writeValueAsString(produitDto);
        //object
        Produit produitToSave = new Produit(
                produitDto.produitId(),
                produitDto.titre(),
                produitDto.description(),
                produitDto.quantiteStock(),
                produitDto.imageUrl(),
                produitDto.prix(),
                produitDto.dateAjout(),
                produitDto.categorie(),
                produitDto.typeProduit(),
                produitDto.website()
        );
        //given
        given(this.produitService.save(produitToSave,null)).willReturn(produitToSave);
        //when and then
        mockMvc.perform(MockMvcRequestBuilders.post(url+"/produits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonDto)
                )
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("produit crée"))
                .andExpect(jsonPath("$.data",Matchers.notNullValue()))
                .andExpect(jsonPath("$.data.description").value("Latest model with advanced features"))
                .andExpect(jsonPath("$.data.quantiteStock").value(50));

    }*/

    @Test
    void updateSuccess() throws Exception {
        // dto
        Website website1 = new Website(1L,"http://google.com",null);
        ProduitDto produitDto = new ProduitDto(1L, "Apple iPhone 14 update", "Latest model with advanced features", 50, 999.99, null, null, null, website1,null,null,null,null);

        String jsonDto = objectMapper.writeValueAsString(produitDto);
        //object
        Produit produitToSave = new Produit(
                produitDto.produitId(),
                produitDto.titre(),
                produitDto.description(),
                produitDto.quantiteStock(),
                produitDto.prix(),
                produitDto.dateAjout(),
                produitDto.categorie(),
                produitDto.typeProduit(),
                produitDto.website()
        );
        //given
        given(this.produitService.update(Mockito.any(Produit.class),eq(1L))).willReturn(produitToSave);
        //when and then
        mockMvc.perform(MockMvcRequestBuilders.patch(url+"/produits/{produitsId}",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonDto)
                )
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("produit mis a jour"))
                .andExpect(jsonPath("$.data",Matchers.notNullValue()))
                .andExpect(jsonPath("$.data.description").value("Latest model with advanced features"))
                .andExpect(jsonPath("$.data.quantiteStock").value(50));

    }

    @Test
    void updateNotSuccess() throws Exception {
        // dto
        Website website1 = new Website(1L,"http://google.com",null);
        ProduitDto produitDto = new ProduitDto(1L, "Apple iPhone 14 update", "Latest model with advanced features", 50, 999.99, null, null, null, website1,null,null,null,null);

        String jsonDto = objectMapper.writeValueAsString(produitDto);
        //object
        Produit produitToSave = new Produit(
                produitDto.produitId(),
                produitDto.titre(),
                produitDto.description(),
                produitDto.quantiteStock(),
                produitDto.prix(),
                produitDto.dateAjout(),
                produitDto.categorie(),
                produitDto.typeProduit(),
                produitDto.website()
        );
        //given
        given(this.produitService.update(Mockito.any(Produit.class),eq(1L))).willThrow(new ObjectNotFoundException(Produit.class.getSimpleName(),1L));
        //when and then
        mockMvc.perform(MockMvcRequestBuilders.patch(url+"/produits/{produitsId}",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonDto)
                )
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Nous ne retrouvons pas l'entité Produit avec id 1"))
                .andExpect(jsonPath("$.data",Matchers.nullValue()));

    }

    @Test
    void deleteSuccess() throws Exception {
        doNothing().when(this.produitService).deleteById(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete(url+"/produits/{produitsId}",1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("produit supprimé"))
                .andExpect(jsonPath("$.data",Matchers.nullValue()));

    }

    @Test
    void deleteNotSuccess() throws Exception {
        doThrow(new ObjectNotFoundException(Produit.class.getSimpleName(),1L)).when(this.produitService).deleteById(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete(url+"/produits/{produitsId}",1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Nous ne retrouvons pas l'entité Produit avec id 1"))
                .andExpect(jsonPath("$.data",Matchers.nullValue()));

    }
}