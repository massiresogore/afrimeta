package com.msr.cg.afrimeta.commentaire;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msr.cg.afrimeta.categorie.Categorie;
import com.msr.cg.afrimeta.clientUser.ClientUser;
import com.msr.cg.afrimeta.commentaire.converter.CommentaireToCommentaireDtoConverter;
import com.msr.cg.afrimeta.couleur.Couleur;
import com.msr.cg.afrimeta.produit.Produit;
import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import com.msr.cg.afrimeta.typeproduit.TypeProduit;
import com.msr.cg.afrimeta.website.Website;
import org.hamcrest.Matchers;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
//@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("dev")
class CommentaireControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CommentaireService commentaireService;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${api.endpoint.base-url}")
    String url;


    @Autowired
    CommentaireToCommentaireDtoConverter commentaireToCommentaireDtoConverter;

    List<Commentaire> commentaires = new ArrayList<>();

    @BeforeEach
    void setUp() {
        //Client
        ClientUser clientUser = new ClientUser();
        clientUser.setEmail("m@gmail.com");
        clientUser.setPassword("MZMZMZMZMZMZZM");
        clientUser.setRole("ADMIN USER");
        clientUser.setEnable(true);

        //Produit
        Website website1 = new Website(1L,"http://google.com",null);
        TypeProduit typeProduit1 = new TypeProduit(1L,"Mango");
        Categorie categorie = new Categorie(1L,"Dior");

        Couleur couleur1 = new Couleur("Yellow");
        Couleur couleur3 = new Couleur("Green");
        List<Couleur> couleurs = new ArrayList<>();
        couleurs.add(couleur1);
        couleurs.add(couleur3);

        Produit produit1 = new Produit(1L, "Apple iPhone 14", "Latest model with advanced features", 50, 999.99, LocalDate.now(), categorie, typeProduit1, website1);

        produit1.setCouleurs(couleurs);
        //commentaire
        Commentaire commentaire1 = new Commentaire(LocalDateTime.now(),"J'adore ce produit",clientUser,produit1);
        Commentaire commentaire2 = new Commentaire(LocalDateTime.now(),"J'adore ce produit vraiment",clientUser,produit1);
        Commentaire commentaire3 = new Commentaire(LocalDateTime.now(),"J'adore ce produit vraiment, c'est trop génial",clientUser,produit1);
        commentaires.add(commentaire1);
        commentaires.add(commentaire2);
        commentaires.add(commentaire3);
    }

    @Test
    void getAll() throws Exception {
        //Given
        given(this.commentaireService.findAll()).willReturn(commentaires);
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.get(url+"/commentaires")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true));
    }

    @Test
    void getByIdSuccess() throws Exception {
        //Given
        given(this.commentaireService.findById(1L)).willReturn(commentaires.get(0));
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.get(url+"/commentaires/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.data.contenu").value("J'adore ce produit"));
    }
    @Test
    void getByIdNotFound() throws Exception {
        //Given
        given(this.commentaireService.findById(1L)).willThrow(ObjectNotFoundException.class);

        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.get(url+"/commentaires/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.data", Matchers.nullValue()));
    }
/*

    @Test
    void update() throws Exception {
        //Mode commentaire
        ModeCommentaire modeCommentaire1 = new ModeCommentaire();
        modeCommentaire1.setNom("Aitel Money");
        //ClientUser
        ClientUser clientUser = new ClientUser("emmano","m@gmail.com","MZMZMZMZMZMZZM",true,"ADMIN USER",null);
        Commande commande1 = new Commande(1L, LocalDateTime.now(),LocalDateTime.now(),22,"33 rue bandas",22,7,clientUser);
        //Maiement
        Commentaire commentaire1 = new Commentaire(1L, LocalDateTime.now(),"description1",modeCommentaire1,commande1);

        String commentaireJson = objectMapper.writeValueAsString(commentaire1);
        //Given
        given(this.commentaireService.update(commentaire1,1L)).willReturn(commentaire1);

        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.patch(url+"/commentaires/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(commentaireJson)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.data", Matchers.notNullValue()));
    }
*/

    @Test
    void delete() throws Exception {
        doNothing().when(commentaireService).deleteById(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete(url+"/commentaires/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("commentaire annulé"))
                .andExpect(jsonPath("$.data",Matchers.nullValue()));
    }

    @Test
    void deleteByIdNotFound() throws Exception {
        doThrow(new ObjectNotFoundException(Commentaire.class.getSimpleName(),112L)).when(commentaireService).deleteById(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete(url+"/commentaires/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Nous ne retrouvons pas l'entité Commentaire avec id 112"))
                .andExpect(jsonPath("$.data",Matchers.nullValue()));
    }
}