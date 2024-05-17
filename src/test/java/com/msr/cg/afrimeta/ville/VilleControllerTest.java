package com.msr.cg.afrimeta.ville;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msr.cg.afrimeta.system.StatusCode;
import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import com.msr.cg.afrimeta.ville.dto.VilleDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)// false désactive la sécurité
@ActiveProfiles("dev")
class VilleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    VilleService villeService;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${api.endpoint.base-url}")
    String url;

    List<Ville> villeList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        //Initialise fake liste Ville
        Ville paris = new Ville(1L,"Paris");
        Ville mossaka = new Ville(2L,"mossaka");
        Ville allemagne = new Ville(3L,"allemagne");
        Ville usa = new Ville(4L,"usa");
        Ville imfondo = new Ville(5L,"imfondo");
        villeList.add(paris);
        villeList.add(mossaka);
        villeList.add(allemagne);
        villeList.add(usa);
        villeList.add(imfondo);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getVillesSuccess() throws Exception {
        //Given
        given(villeService.findAll()).willReturn(villeList);

        //When Then
        this.mockMvc.perform(MockMvcRequestBuilders.get(url+"/villes")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("toutes les villes"))
                .andExpect(jsonPath("$.data", Matchers.hasSize(villeList.size())))
                .andExpect(jsonPath("$.data[0].nom").value("Paris"))
                .andExpect(jsonPath("$.data[1].nom").value("mossaka"));
    }

    @Test
    void findVilleByIdSuccess() throws Exception {
        //Given
        given(this.villeService.findById(1)).willReturn(villeList.get(0));
        //When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.get(url+"/villes/{villeId}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("ville retrouvée"))
                .andExpect(jsonPath("$.data.nom").value("Paris"));
    }
    @Test
    void findVilleByIdNotFound() throws Exception {
        //Given
        given(this.villeService.findById(111)).willThrow(new ObjectNotFoundException("ville",Long.parseLong("111")));
        //When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.get(url+"/villes/{villeId}", 111)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Nous ne retrouvons pas l'entité ville avec id 111"))
                .andExpect(jsonPath("$.data",Matchers.nullValue()));
    }

    @Test
    void saveVille() throws Exception {
        //VilleDto ready to update
        VilleDto villeDto = new VilleDto(1L,"Paris save new ville");

        //Convert VilleDto to json
        String jsonMapper = objectMapper.writeValueAsString(villeDto);

        //Convert Villedto to ville
        Ville villesaved = new Ville(villeDto.villeId(), villeDto.nom());

        //Given
        given(this.villeService.save(Mockito.any(Ville.class))).willReturn(villesaved);
        //When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.post(url+"/villes")
                        .content(jsonMapper)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("ville ajoutée"))
                .andExpect(jsonPath("$.data.nom").value("Paris save new ville"));
    }


    @Test
    void updateVilleByIdSuccess() throws Exception {
        //VilleDto ready to update
        VilleDto villeDto = new VilleDto(1L,"Paris update");

        //Convert VilleDto to json
        String jsonMapper = objectMapper.writeValueAsString(villeDto);

        //Convert Villedto to ville
        Ville villeUpdated = new Ville(villeDto.villeId(), villeDto.nom());

        //Given
        given(this.villeService.update(Mockito.any(Ville.class),eq(1L))).willReturn(villeUpdated);

        //When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.put(url+"/villes/{villeId}/update", 1)
        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("ville mis à jour"))
                .andExpect(jsonPath("$.data.villeId").value(villeUpdated.getVilleId()))
                .andExpect(jsonPath("$.data.nom").value(villeUpdated.getNom()));
        ;
    }

    @Test
    void updateVilleByIdNotFound() throws Exception {
        //VilleDto ready to update
        VilleDto villeDto = new VilleDto(10L,"Paris update");

        //Convert VilleDto to json
        String jsonMapper = objectMapper.writeValueAsString(villeDto);

        //Given
        given(this.villeService.update(Mockito.any(Ville.class),eq(10L))).willThrow(new ObjectNotFoundException("ville",Long.parseLong("10")));

        //When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.put(url+"/villes/{villeId}/update", 10)
        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Nous ne retrouvons pas l'entité ville avec id 10"))
                .andExpect(jsonPath("$.data", Matchers.nullValue()));
        ;
    }

    @Test
    void deleteVilleByIdSuccess() throws Exception {
        //Given
        doNothing().when(this.villeService).deleteById(1L);


        //When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.delete(url+"/villes/{villeId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("ville supprimée"))
                .andExpect(jsonPath("$.data", Matchers.nullValue()));
        ;
    }

    @Test
    void deleteVilleByIdNotFound() throws Exception {
        //Given
        doThrow(new ObjectNotFoundException("ville",Long.parseLong("13"))).when(this.villeService).deleteById(13L);


        //When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.delete(url+"/villes/{villeId}", 13)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Nous ne retrouvons pas l'entité ville avec id 13"))
                .andExpect(jsonPath("$.data", Matchers.nullValue()));
        ;
    }



}