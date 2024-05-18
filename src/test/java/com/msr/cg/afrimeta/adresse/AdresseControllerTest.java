package com.msr.cg.afrimeta.adresse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msr.cg.afrimeta.adresse.dto.AdresseDto;
import com.msr.cg.afrimeta.system.StatusCode;
import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import com.msr.cg.afrimeta.ville.Ville;
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

import static org.mockito.BDDMockito.given;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("dev")
class AdresseControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    AdresseService adresseService;

    @Value("${api.endpoint.base-url}")
    String url;

    @Autowired
    ObjectMapper mapper;

    List<Adresse> adresses = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Ville paris = new Ville(1L,"Paris");
        Ville mossaka = new Ville(2L,"mossaka");
        Ville allemagne = new Ville(3L,"allemagne");
        Ville usa = new Ville(4L,"usa");
        Ville imfondo = new Ville(5L,"imfondo");

        Adresse adresse1 = new Adresse("40",77440,paris);
        Adresse adresse2 = new Adresse("40",77440,mossaka);
        Adresse adresse3 = new Adresse("40",77440,allemagne);
        Adresse adresse4 = new Adresse("40",77440,usa);
        Adresse adresse5 = new Adresse("40",77440,imfondo);
        adresses.add(adresse1);
        adresses.add(adresse2);
        adresses.add(adresse3);
        adresses.add(adresse4);
        adresses.add(adresse5);
    }

    @Test
    void store() throws Exception {
        Ville paris = new Ville(1L,"Paris");
        AdresseDto adresseDto = new AdresseDto(null,"090909",77440,1L);

        Adresse adresse = new Adresse(adresseDto.numero(),adresseDto.cp(),paris);
      String jsonAdresses =   mapper.writeValueAsString(adresseDto);

        //Given
        given(this.adresseService.save(Mockito.any(Adresse.class))).willReturn(adresse);
        //When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.post(url+"/adresses")
                .content(jsonAdresses)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.message").value("adresse ajoutée"))
                .andExpect(jsonPath("$.data.numero").value("090909"))
                .andExpect(jsonPath("$.data.numero").exists());
    }

    @Test
    void getAdresses() throws Exception {
        //Given
        given(this.adresseService.findAll()).willReturn(adresses);
        //When Then
        this.mockMvc.perform(MockMvcRequestBuilders.get(url+"/adresses")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("toutes les adresses"))
                .andExpect(jsonPath("$.data", Matchers.hasSize(adresses.size())))
                .andExpect(jsonPath("$.data[0].numero").value("40"))
                .andExpect(jsonPath("$.data[1].numero").value("40"));
    }

    @Test
    void findById() throws Exception {
        Ville paris = new Ville(1L,"Paris");
        Adresse adresse1 = new Adresse("40",77440,paris);

        //Given
        given(this.adresseService.findById(1L)).willReturn(adresse1);
        //When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.get(url+"/adresses/{adresseId}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("adresse retrouvée"))
                .andExpect(jsonPath("$.data.numero").value("40"));
    }
    @Test
    void findByIdNotFound() throws Exception {
        //Given
        given(this.adresseService.findById(13L)).willThrow(new ObjectNotFoundException("adresse",13L));
        //When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.get(url+"/adresses/{adresseId}", 13L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Nous ne retrouvons pas l'entité adresse avec id 13"))
                .andExpect(jsonPath("$.data",Matchers.nullValue()));
    }

    @Test
    void updateAdresseById() throws Exception {
        // simule un object à mettre à jours
        Ville paris = new Ville(1L,"Paris");
        AdresseDto adresseDto =new AdresseDto(1L,"090909",77440,1L);

        //Convertion en json
        String jsonAdresses = mapper.writeValueAsString(adresseDto);

        // dto to object
        Adresse adresse = new Adresse(adresseDto.adresseId(),adresseDto.numero(),adresseDto.cp(),paris);

        //given
        given(this.adresseService.update(Mockito.any(Adresse.class), Mockito.anyLong())).willReturn(adresse);

        //When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.patch(url+"/adresses/{adresseId}", 1)
                        .content(jsonAdresses)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.message").value("adresse mis à jour"))
                .andExpect(jsonPath("$.data.numero").value("090909"))
                .andExpect(jsonPath("$.data.cp").value(77440));
    } @Test
    void updateAdresseByIdNotFound() throws Exception {
        // simule un object à mettre à jours
        Ville paris = new Ville(1L,"Paris");
        AdresseDto adresseDto =new AdresseDto(13L,"090909",77440,1L);

        //Convertion en json
        String jsonAdresses = mapper.writeValueAsString(adresseDto);

        // dto to object
        Adresse adresse = new Adresse(adresseDto.adresseId(),adresseDto.numero(),adresseDto.cp(),paris);

        //given
        given(this.adresseService.update(Mockito.any(Adresse.class), Mockito.anyLong())).willThrow(new ObjectNotFoundException("adresse",13L));

        //When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.patch(url+"/adresses/{adresseId}", 13)
                        .content(jsonAdresses)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Nous ne retrouvons pas l'entité adresse avec id 13"))
                .andExpect(jsonPath("$.data",Matchers.nullValue()));
    }



    @Test
    void deleteAdresseById() throws Exception {
        //given
        doNothing().when(this.adresseService).deleteById(1L);

        //When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.delete(url+"/adresses/{adresseId}", 1L)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("adresse supprimée"))
                .andExpect(jsonPath("$.data",Matchers.nullValue()));
    }

    @Test
    void deleteAdresseByIdNotFound() throws Exception {
        //given
       doThrow(new ObjectNotFoundException("adresse",13L)).when(this.adresseService).deleteById(13L);

        //When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.delete(url+"/adresses/{adresseId}", 13)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Nous ne retrouvons pas l'entité adresse avec id 13"))
                .andExpect(jsonPath("$.data",Matchers.nullValue()));

    }
}