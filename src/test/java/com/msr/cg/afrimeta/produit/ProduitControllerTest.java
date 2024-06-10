package com.msr.cg.afrimeta.produit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msr.cg.afrimeta.categorie.Categorie;
import com.msr.cg.afrimeta.couleur.Couleur;
import com.msr.cg.afrimeta.image.Image;
import com.msr.cg.afrimeta.produit.dto.converter.ProduitToProduitDtoConverter;
import com.msr.cg.afrimeta.produit.dto.dto.ProduitDto;
import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import com.msr.cg.afrimeta.taille.Taille;
import com.msr.cg.afrimeta.typeproduit.TypeProduit;
import com.msr.cg.afrimeta.website.Website;
import com.msr.cg.afrimeta.website.WebsiteService;
import com.msr.cg.afrimeta.website.dto.SingleWebsiteResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @MockBean
    WebsiteService websiteService;


    @Autowired
    ObjectMapper objectMapper;

    @Value("${api.endpoint.base-url}")
    String url;

    List<Produit> produits = new ArrayList<>();

    @Autowired
    ProduitToProduitDtoConverter produitToProduitDtoConverter;

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

        //Image
        Image image1 = new Image("image/jpg","/User/usr/msr","massire1.jpg");
        Image image2 =  new Image("image/jpg","/User/usr/msr","massire1.jpg");
        produit1.addImage(image1);
        produit2.addImage(image2);
        produit3.addImage(image2);
        produit4.addImage(image2);
        produit5.addImage(image2);

        //Categorie
        Categorie defaultCategorie = new Categorie("default categorie");
        produit1.setCategorie(defaultCategorie);
        produit2.setCategorie(defaultCategorie);
        produit3.setCategorie(defaultCategorie);
        produit4.setCategorie(defaultCategorie);
        produit5.setCategorie(defaultCategorie);

        //Type produit
        TypeProduit defaultTypeProduit = new TypeProduit("default type produit");
        produit1.setTypeProduit(defaultTypeProduit);
        produit2.setTypeProduit(defaultTypeProduit);
        produit3.setTypeProduit(defaultTypeProduit);
        produit4.setTypeProduit(defaultTypeProduit);
        produit5.setTypeProduit(defaultTypeProduit);

        //Couleur
        Couleur defaultCouleur = new Couleur("default couleur");
        //a faire
        List<Couleur> couleurs = new ArrayList<>();
        couleurs.add(defaultCouleur);

        produit1.setCouleurs(couleurs);
        produit2.setCouleurs(couleurs);
        produit3.setCouleurs(couleurs);
        produit4.setCouleurs(couleurs);
        produit5.setCouleurs(couleurs);



        produits.add(produit1);
        produits.add(produit2);
        produits.add(produit3);
        produits.add(produit4);
        produits.add(produit5);
    }

    @Test
    void getAll() throws Exception {
        //given
        Pageable pageable = PageRequest.of(0,20);
        PageImpl<Produit> produitPage = new PageImpl<>(produits, pageable,this.produits.size());

        given(this.produitService.findAllPageable(Mockito.any(Pageable.class))).willReturn(produitPage);

        MultiValueMap<String,String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("page","0");
        requestParams.add("size","20");
        //when and Then
        mockMvc.perform(MockMvcRequestBuilders.get(url+"/produits/bataclan")
                        .accept(MediaType.APPLICATION_JSON).params(requestParams))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("tous les produits de website avec leur catégorie et type"))
                .andExpect(jsonPath("$.data.content", Matchers.hasSize(5)));
    }

    @Test
    void showSuccess() throws Exception {
        //Catégorie
        Categorie defaultCategorie = new Categorie("default categorie");

        //Type produit and taille
        TypeProduit defaultTypeProduit = new TypeProduit("default type produit");
        Taille defaultTaille = new Taille("default taille");
        defaultTypeProduit.addTaille(defaultTaille);

        //Couleur
        Couleur defaultCouleur = new Couleur("default couleur");
        List<Couleur> couleurs = new ArrayList<>();
        couleurs.add(defaultCouleur);

        //Image
        Image image = new Image("image/jpeg","http://localhost:8080/produit.jpeg","ordinateur");
        List<Image> images = new ArrayList<>();
        images.add(image);

        List<String> imagePaths = new ArrayList<>();
        images.stream().peek(image1 -> imagePaths.add(image1.getName()));

        // dto
        Website website1 = new Website(1L,"http://google.com",null);
        SingleWebsiteResponse websiteResponse = new SingleWebsiteResponse(website1.getWebsiteId(),website1.getWebsiteUrl());
        ProduitDto produitDto = new ProduitDto(null, "Apple iPhone 14", "Latest model with advanced features", 50,  999.99, LocalDate.now(), defaultCategorie, defaultTypeProduit, website1,couleurs,imagePaths,websiteResponse);

        /*********/
        //object
        Produit produit = new Produit();
        produit.setTitre(produitDto.titre());
        produit.setDescription(produitDto.description());
        produit.setQuantiteStock(produitDto.quantiteStock());
        produit.setPrix(produitDto.prix());
        produit.setCategorie(defaultCategorie);
        produit.setTypeProduit(defaultTypeProduit);
        produit.addCouleur(defaultCouleur);
        produit.setImages(images);
        produit.setWebsite(website1);


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
       //Catégorie
       Categorie defaultCategorie = new Categorie("default categorie");

       //Type produit and taille
       TypeProduit defaultTypeProduit = new TypeProduit("default type produit");
       Taille defaultTaille = new Taille("default taille");
       defaultTypeProduit.addTaille(defaultTaille);

       //Couleur
       Couleur defaultCouleur = new Couleur("default couleur");
       List<Couleur> couleurs = new ArrayList<>();
       couleurs.add(defaultCouleur);

       //Image
       Image image = new Image("image/jpeg","http://localhost:8080/produit.jpeg","ordinateur");
       List<Image> images = new ArrayList<>();
       images.add(image);

       List<String> imagePaths = new ArrayList<>();
       images.stream().peek(image1 -> imagePaths.add(image1.getName()));

       // dto
       Website website1 = new Website(1L,"http://google.com",null);
       SingleWebsiteResponse websiteResponse = new SingleWebsiteResponse(website1.getWebsiteId(),website1.getWebsiteUrl());
       ProduitDto produitDto = new ProduitDto(null, "Apple iPhone 14", "Latest model with advanced features", 50,  999.99, LocalDate.now(), defaultCategorie, defaultTypeProduit, website1,couleurs,imagePaths,websiteResponse);

       String jsonProduit = objectMapper.writeValueAsString(produitDto);
       *//*********//*
       //object
       Produit produit = new Produit();
       produit.setTitre(produitDto.titre());
       produit.setDescription(produitDto.description());
       produit.setQuantiteStock(produitDto.quantiteStock());
       produit.setPrix(produitDto.prix());
       produit.setCategorie(defaultCategorie);
       produit.setTypeProduit(defaultTypeProduit);
       produit.addCouleur(defaultCouleur);
       produit.setImages(images);
       produit.setWebsite(website1);
        //given
       given(this.websiteService.findById(1L)).willReturn(website1);

       given(this.produitService.save(produit)).willReturn(produit);
        //when and then
        mockMvc.perform(MockMvcRequestBuilders.post(url+"/produits/{websiteId}",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonProduit)
                )
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("produit crée"))
                .andExpect(jsonPath("$.data",Matchers.notNullValue()))
                .andExpect(jsonPath("$.data.description").value("Latest model with advanced feature"))
                .andExpect(jsonPath("$.data.quantiteStock").value(50));

    }*/

   /* @Test
    void updateSuccess() throws Exception {
        // dto
        Website website1 = new Website(1L,"http://google.com",null);
        ProduitDto produitDto = new ProduitDto(1L, "Apple iPhone 14 update", "Latest model with advanced features", 50, 999.99, null, null, null, website1,null,null,null);

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

    }*/
/*

    @Test
    void updateNotSuccess() throws Exception {
        // dto
        Website website1 = new Website(1L,"http://google.com",null);
        ProduitDto produitDto = new ProduitDto(1L, "Apple iPhone 14 update", "Latest model with advanced features", 50, 999.99, null, null, null, website1,null,null,null);

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
*/

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