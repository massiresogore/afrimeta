package com.msr.cg.afrimeta.clientUser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msr.cg.afrimeta.clientUser.converter.ClientUserDtoToClientUserConverter;
import com.msr.cg.afrimeta.clientUser.dto.ClientUserDto;
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
        users = new ArrayList<>();
        ClientUser user1 = new ClientUser(
                1L,
                "Mona",
                "e@gmail.com",
                "12345678",
                true,
                "Admin"
        );
        ClientUser user2 = new ClientUser(
                2L,
                "Mona",
                "e@gmail.com",
                "12345678",
                true,
                "Admin"
        );
        ClientUser user3 = new ClientUser(
                3L,
                "Mona",
                "e@gmail.com",
                "12345678",
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
                .andExpect(jsonPath("$.data[0].email").value(this.users.get(0).getEmail()));
    }

    @Test
    void getUserByIdSuccess() throws Exception {
        ClientUser user2 = new ClientUser(
                2L,
                "Mona",
                "e@gmail.com",
                "12345678",
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
                .andExpect(jsonPath("$.data.email").value(user2.getEmail()));

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
                "moile@gmail.com",
                "1273637383",
                true,
                "0909090099"
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
                12L,
                "Parolie",
                "moile@gmail.com",
                "1273637383",
                true,
                "0909090099"
        );


        //Stringify dto o json
        String jsonDtoUser = objectMapper.writeValueAsString(userDto);

        //Convert dto to objec
        ClientUser user = new ClientUser(
                12L,
                "Mona",
                "e@gmail.com",
                "12345678",
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



        //Dto
        ClientUserDto userDto = new ClientUserDto(
                null,
                "Parolie",
                "moile@gmail.com",
                "1273637383",
                true,
                "0909090099"
        );

        //Stringify dto o json
        String jsonDtoUser = objectMapper.writeValueAsString(userDto);

        //Convert dto to objec
        ClientUser clientUser = new ClientUser(
                "Mona",
                "e@gmail.com",
                "12345678",
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
                .andExpect(jsonPath("$.data.role").value(clientUser.getRole()));
    }
}




















