package com.msr.cg.afrimeta.clientUser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msr.cg.afrimeta.adresse.Adresse;
import com.msr.cg.afrimeta.clientUser.converter.ClientUserDtoToClientUserConverter;
import com.msr.cg.afrimeta.clientUser.dto.ClientUserDto;
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

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("dev")
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ClientUserService clientUserService;

    @Autowired
    ObjectMapper objectMapper;


    @Value("${api.endpoint.base-url}")
    String url;

    List<ClientUser> users;

    @BeforeEach
    void setUp() {
        Ville paris = new Ville(1L,"Paris");
        Adresse adresse = new Adresse(1L,"40",77440,paris);

        users = new ArrayList<>();
        ClientUser user1 = new ClientUser(
                "Mona",
                "Berthe",
                "e@gmail.com",
                "12345678",
                "0909090099",
                adresse,
                "Patron",
                true,
                "Admin"
        );
        ClientUser user2 = new ClientUser(
                "Parolie",
                "Moile",
                "moile@gmail.com",
                "12345678",
                "0909090099",
                adresse,
                "Patron",
                true,
                "Admin"
        );
        ClientUser user3 = new ClientUser(
                "Lianne",
                "Piole",
                "piole@gmail.com",
                "12345678",
                "0909090099",
                adresse,
                "Patron",
                true,
                "Admin"
        );

        users.add(user1);
        users.add(user2);
        users.add(user3);

    }

    @Test
    void getAllUsers() throws Exception {
        //Given
        given(this.clientUserService.findAll()).willReturn(users);

        //When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.get(url+"/users"))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("tous les users"))
                .andExpect(jsonPath("$.data", Matchers.hasSize(this.users.size())))
                .andExpect(jsonPath("$.data[0].nom").value(this.users.get(0).getNom()));
    }

    @Test
    void getUserByIdSuccess() throws Exception {
        Ville paris = new Ville(1L,"Paris");
        Adresse adresse = new Adresse(1L,"40",77440,paris);
        ClientUser user2 = new ClientUser(
                1L,
                "Parolie",
                "Moile",
                "moile@gmail.com",
                "12345678",
                "0909090099",
                adresse,
                "Patron",
                true,
                "Admin"
        );
        //Given
        given(this.clientUserService.findById(1L)).willReturn(user2);
        //When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.get(url+"/users/{userId}", 1L)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("l'utilisateur trouvé"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.nom").value(user2.getNom()));

    }

    @Test
    void getUserByIdNotFound() throws Exception {
        //Given
        given(this.clientUserService.findById(13L)).willThrow(new ObjectNotFoundException("user",13L));

        //When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.get(url+"/users/{userId}", 13)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Nous ne retrouvons pas l'entité user avec id 13"))
                .andExpect(jsonPath("$.data",Matchers.nullValue()));
    }

    @Test
    void updateUserByIdNotFound() throws Exception {
        //Dto
        ClientUserDto userDto = new ClientUserDto(
                12L,
                "Parolie",
                "Moile",
                "moile@gmail.com",
                "12345678",
                "0909090099",
                "Boss",
                true,
                "Admin",
                new Adresse(null,"9898989",999090,new Ville("Malie"))
        );

        //Stringify dto o json
        String jsonDtoUser = objectMapper.writeValueAsString(userDto);

        given(this.clientUserService.update(Mockito.any(ClientUser.class),eq(12L))).willThrow(new ObjectNotFoundException("user",12L));

        this.mockMvc.perform(MockMvcRequestBuilders.patch(url+"/users/{userId}", 12)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonDtoUser)
                )
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Nous ne retrouvons pas l'entité user avec id 12"))
                .andExpect(jsonPath("$.data",Matchers.nullValue()));
    }

    @Test
    void updateUserByIdSuccess() throws Exception {
        //Dto
        ClientUserDto userDto = new ClientUserDto(
                null,
                "Parolie",
                "Moile",
                "moile@gmail.com",
                "12345678",
                "0909090099",
                "Boss",
                true,
                "Admin",
                new Adresse(1L,"9898989",999090,new Ville(1L,"Malie"))

        );

        Ville paris = new Ville(1L,"Paris");
        Adresse adresse = new Adresse(1L,"77440",77440,paris);

        //Stringify dto o json
        String jsonDtoUser = objectMapper.writeValueAsString(userDto);

        //Convert dto to objec
        ClientUser user = new ClientUser(
                12L,
                "Mona",
                "Berthe",
                "e@gmail.com",
                "12345678",
                "0909090099",
                adresse,
                "Patron",
                true,
                "Admin"
        );

        given(this.clientUserService.update(Mockito.any(ClientUser.class),eq(12L))).willReturn(user);
        this.mockMvc.perform(MockMvcRequestBuilders.patch(url+"/users/{userId}", 12)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonDtoUser)
                )
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("user mis à jours"))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void deleteUserByIdSuccess() throws Exception {
        //Given
        doNothing().when(this.clientUserService).deleteById(1L);
        //When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.delete(url+"/users/{userId}", 1)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("l'utilisateur supprimé"))
                .andExpect(jsonPath("$.data", Matchers.nullValue()));
    }
    @Test
    void deleteUserByIdNotFound() throws Exception {
        //given
        doThrow(new ObjectNotFoundException("user",1L)).when(this.clientUserService).deleteById(1L);
        //When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.delete(url+"/users/{userId}", 1)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Nous ne retrouvons pas l'entité user avec id 1"))
                .andExpect(jsonPath("$.data",Matchers.nullValue()));
    }

    @Test
    void saveUser() throws Exception {


        Ville paris = new Ville(null,"Paris");
        // simulation de Adresse existante
        Adresse adresse = new Adresse(null,"77440",77440,paris);
        //Dto
        ClientUserDto userDto = new ClientUserDto(
                null,
                "Parolie",
                "Moile",
                "moile@gmail.com",
                "12345678",
                "0909090099",
                "Boss",
                true,
                "Admin",
                adresse
        );

        //Stringify dto o json
        String jsonDtoUser = objectMapper.writeValueAsString(userDto);

        //Convert dto to objec
        ClientUser clientUser = new ClientUser(
                "Parolie",
                "Moile",
                "moile@gmail.com",
                "12345678",
                "0909090099",
                adresse,
                "Boss",
                true,
                "Admin"
        );


        //Given
        given(this.clientUserService.save(Mockito.any(ClientUser.class))).willReturn(clientUser);
        //When and Then
        this.mockMvc.perform(MockMvcRequestBuilders.post(url+"/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonDtoUser)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("user créé"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.nom").value(clientUser.getNom()));
    }
}




















