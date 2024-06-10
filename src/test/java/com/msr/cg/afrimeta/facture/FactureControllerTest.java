package com.msr.cg.afrimeta.facture;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msr.cg.afrimeta.facture.dto.FactureDto;
import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("dev")
class FactureControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    FactureService factureService;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${api.endpoint.base-url}")
    String url;

    List<Facture> factures = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Facture facture1 = new Facture(1L, LocalDate.now(), 22, 22, 2);
        Facture facture2 = new Facture(2L, LocalDate.now(), 23, 21, 3);
        Facture facture3 = new Facture(3L, LocalDate.now(), 24, 11, 1);
        Facture facture4 = new Facture(4L, LocalDate.now(), 25, 99, 3);

        factures.add(facture1);
        factures.add(facture2);
        factures.add(facture3);
        factures.add(facture4);

    }

    @Test
    void getAllFactures() throws Exception {
        //Given
        given(factureService.findAll()).willReturn(factures);

        mockMvc.perform(MockMvcRequestBuilders.get(url+"/factures")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].factureId").value(1L))
                .andExpect(jsonPath("$.data[1].factureId").value(2L))
                .andExpect(jsonPath("$.data[2].factureId").value(3L));
    }

    @Test
    void getFactureById() throws Exception {
        given(factureService.findById(1L)).willReturn(factures.get(0));
        mockMvc.perform(MockMvcRequestBuilders.get(url+"/factures/{factureId}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("la facture trouvée"))
                .andExpect(jsonPath("$.data.factureId").value(1L))
                .andExpect(jsonPath("$.data.totalHorsTaxe").value(22));
    }

    @Test
    void updateFacture() throws Exception {
        //DTo
        FactureDto factureDto = new FactureDto(1L, LocalDate.now(), 22, 22, 2);

        //Object
        Facture facture = new Facture(factureDto.factureId(),factureDto.factureDate(),factureDto.totalHorsTaxe(),factureDto.totalToutTaxeComprise(),factureDto.totalTva());
        //string to json
        String json = objectMapper.writeValueAsString(factureDto);

        given(this.factureService.update(Mockito.any(Facture.class),eq(1L))).willReturn(facture);

        mockMvc.perform(MockMvcRequestBuilders.patch(url+"/factures/{factureId}", 1L)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("facture mis à jours"))
                .andExpect(jsonPath("$.data.factureId").value(facture.getFactureId()))
                .andExpect(jsonPath("$.data.totalHorsTaxe").value(facture.getTotalHorsTaxe()));
    }

    @Test
    void updateFactureNotFound() throws Exception {
        //DTo
        FactureDto factureDto = new FactureDto(1L, LocalDate.now(), 22, 22, 2);

        //Object
        Facture facture = new Facture(factureDto.factureId(),factureDto.factureDate(),factureDto.totalHorsTaxe(),factureDto.totalToutTaxeComprise(),factureDto.totalTva());
        //string to json
        String json = objectMapper.writeValueAsString(factureDto);

        given(this.factureService.update(Mockito.any(Facture.class),eq(1L))).willReturn(facture);

        doThrow(new ObjectNotFoundException("facture",1L)).when(this.factureService).update(Mockito.any(Facture.class),eq(1L));

        mockMvc.perform(MockMvcRequestBuilders.patch(url+"/factures/{factureId}", 1L)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Nous ne retrouvons pas l'entité facture avec id 1"))
                .andExpect(jsonPath("$.data", Matchers.nullValue()));
    }

    @Test
    void deleteFactureByI() throws Exception {
        doNothing().when(this.factureService).deleteById(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete(url+"/factures/{factureId}", 1L)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("facture supprimée"))
                .andExpect(jsonPath("$.data", Matchers.nullValue()));
    }

    @Test
    void deleteFactureByIdNotFound() throws Exception {
        doThrow(new ObjectNotFoundException("facture",1L)).when(this.factureService).deleteById(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete(url+"/factures/{factureId}", 1L)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Nous ne retrouvons pas l'entité facture avec id 1"))
                .andExpect(jsonPath("$.data", Matchers.nullValue()));
    }

}