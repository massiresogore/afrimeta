package com.msr.cg.afrimeta.profile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msr.cg.afrimeta.profile.dto.ProfileDto;
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
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
//@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles(value = "dev")
class ProfileControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProfileService profileService;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${api.endpoint.base-url}")
    String url;

    List<Profile> profiles = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Profile profile1 = new Profile(1L,  "Doe", "John", "1234567890", LocalDate.now(), GenreEnum.male, "123 Main St", "Paris", "75001", "France", "http://example.com/johndoe.jpg", "Bio of John Doe");
        Profile profile2 = new Profile(2L,  "Smith", "Jane", "0987654321",LocalDate.now(), GenreEnum.female, "456 Elm St", "Lyon", "69001", "France", "http://example.com/janesmith.jpg", "Bio of Jane Smith");
        Profile profile3 = new Profile(3L,  "Jones", "Alice", "1122334455", LocalDate.now(), GenreEnum.female, "789 Oak St", "Marseille", "13001", "France", "http://example.com/alicejones.jpg", "Bio of Alice Jones");
        Profile profile4 = new Profile(4L,  "Brown", "Bob", "2233445566", LocalDate.now(), GenreEnum.female, "101 Pine St", "Nice", "06000", "France", "http://example.com/bobbrown.jpg", "Bio of Bob Brown");
        Profile profile5 = new Profile(5L,  "White", "Carol", "3344556677",LocalDate.now(), GenreEnum.female, "202 Birch St", "Toulouse", "31000", "France", "http://example.com/carolwhite.jpg", "Bio of Carol White");

        profiles.add(profile1);
        profiles.add(profile2);
        profiles.add(profile3);
        profiles.add(profile4);
        profiles.add(profile5);
    }


    @Test
    void saveProfile() throws Exception {
        //profile dto
        ProfileDto profileDto =   new ProfileDto(
                null,
                "Doe",
                "John",
                "1234567890",
               "2020-08-07",
                GenreEnum.male,
                "123 Main St",
                "Paris",
                "75001",
                "France",
                "http://example.com/johndoe.jpg",
                "Bio of John Doe"
        );
        //stranform to json
        String jsonProfile = objectMapper.writeValueAsString(profileDto);

        //transform to profile
        Profile saveProfile=  new Profile(
                null,
                profileDto.nom(),
                profileDto.prenom(),
                profileDto.numeroTelephone(),
                LocalDate.parse(profileDto.dateNaissance()),
                profileDto.genre(),
                profileDto.addresse(),
                profileDto.ville(),
                profileDto.codePostal(),
                profileDto.pays(),
                profileDto.profilePicturePrl(),
                profileDto.bio()
        );

        //Given
        given(this.profileService.save(Mockito.any(Profile.class))).willReturn(saveProfile);

        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.post(url+"/user/profiles")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonProfile)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.message").value("profile créé"))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.nom").value(saveProfile.getNom()))
                .andExpect(jsonPath("$.data.prenom").value(saveProfile.getPrenom()))
                .andExpect(jsonPath("$.data.numeroTelephone").value(saveProfile.getNumeroTelephone()))
                //.andExpect(jsonPath("$.data.genre").value(saveProfile.getGenre()))
                .andExpect(jsonPath("$.data.addresse").value(saveProfile.getAddresse()))
                .andExpect(jsonPath("$.data.ville").value(saveProfile.getVille()))
                .andExpect(jsonPath("$.data.codePostal").value(saveProfile.getCodePostal()))
                .andExpect(jsonPath("$.data.pays").value(saveProfile.getPays()))
                .andExpect(jsonPath("$.data.profilePicturePrl").value(saveProfile.getProfilePictureUrl()))
                .andExpect(jsonPath("$.data.bio").value(saveProfile.getBio())

        );
    }

    @Test
    void getAllProfiles() throws Exception {
        //given
        given(this.profileService.findAll()).willReturn(this.profiles);
        //when and Then
        mockMvc.perform(MockMvcRequestBuilders.get(url+"/user/profiles")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.message").value("tous les profiles"))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data[0].nom").value(this.profiles.get(0).getNom()))
                .andExpect(jsonPath("$.data[0].prenom").value(this.profiles.get(0).getPrenom()))
                .andExpect(jsonPath("$.data[0].numeroTelephone").value(this.profiles.get(0).getNumeroTelephone()))
                //.andExpect(jsonPath("$.data[0.genre").value(this.profiles.get(0).getGenre()))
                .andExpect(jsonPath("$.data[0].addresse").value(this.profiles.get(0).getAddresse()))
                .andExpect(jsonPath("$.data[0].ville").value(this.profiles.get(0).getVille()))
                .andExpect(jsonPath("$.data[0].codePostal").value(this.profiles.get(0).getCodePostal()))
                .andExpect(jsonPath("$.data[0].pays").value(this.profiles.get(0).getPays()))
                .andExpect(jsonPath("$.data[0].profilePicturePrl").value(this.profiles.get(0).getProfilePictureUrl()))
                .andExpect(jsonPath("$.data[0].bio").value(this.profiles.get(0).getBio())
        );

    }

    @Test
    void getProfileByIdSuccess() throws Exception {
        //Given
        given(this.profileService.findById(1L)).willReturn(this.profiles.get(0));
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.get(url+"/user/profiles/{profileId}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.message").value("le profile trouvé"))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.nom").value(this.profiles.get(0).getNom()))
                .andExpect(jsonPath("$.data.prenom").value(this.profiles.get(0).getPrenom()))
                .andExpect(jsonPath("$.data.numeroTelephone").value(this.profiles.get(0).getNumeroTelephone()))
                //.andExpect(jsonPath("$.data[0.genre").value(this.profiles.get(0).getGenre()))
                .andExpect(jsonPath("$.data.addresse").value(this.profiles.get(0).getAddresse()))
                .andExpect(jsonPath("$.data.ville").value(this.profiles.get(0).getVille()))
                .andExpect(jsonPath("$.data.codePostal").value(this.profiles.get(0).getCodePostal()))
                .andExpect(jsonPath("$.data.pays").value(this.profiles.get(0).getPays()))
                .andExpect(jsonPath("$.data.profilePicturePrl").value(this.profiles.get(0).getProfilePictureUrl()))
                .andExpect(jsonPath("$.data.bio").value(this.profiles.get(0).getBio())
        );
    }
    @Test
    void getProfileByIdNotFound() throws Exception {
        //Given
        given(this.profileService.findById(12L)).willThrow(new ObjectNotFoundException("profile",12L));
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.get(url+"/user/profiles/{profileId}", 12L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.message").value("Nous ne retrouvons pas l'entité profile avec id 12"))
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.data", Matchers.nullValue())
        );
    }

    @Test
    void updateProfileByIdSuccess()throws Exception {
        //profile dto
        ProfileDto profileDto =   new ProfileDto(
                18L,
                "Doe",
                "John",
                "1234567890",
                "2022-09-05",
                GenreEnum.male,
                "123 Main St",
                "Paris",
                "75001",
                "France",
                "http://example.com/johndoe.jpg",
                "Bio of John Doe"
        );
        //stranform to json
        String jsonProfile = objectMapper.writeValueAsString(profileDto);

        //transform to profile
        Profile saveProfile=  new Profile();
            saveProfile.setProfileId(profileDto.profileId());
            saveProfile.setNom(profileDto.nom());
            saveProfile.setPrenom(profileDto.prenom());
            saveProfile.setNumeroTelephone(profileDto.numeroTelephone());
            saveProfile.setAddresse(profileDto.addresse());
            saveProfile.setVille(profileDto.ville());
            saveProfile.setCodePostal(profileDto.codePostal());
            saveProfile.setPays(profileDto.pays());
            saveProfile.setDateNaissance(LocalDate.parse(profileDto.dateNaissance()));
            saveProfile.setProfilePictureUrl(profileDto.profilePicturePrl());
            saveProfile.setBio(profileDto.bio());

        //Given
        given(this.profileService.update(Mockito.any(Profile.class),eq(18L))).willReturn(saveProfile);

        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.patch(url+"/user/profiles/{profileId}", 18L)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonProfile)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.message").value("profile mis à jours"))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.nom").value(saveProfile.getNom()))
                .andExpect(jsonPath("$.data.prenom").value(saveProfile.getPrenom()))
                .andExpect(jsonPath("$.data.numeroTelephone").value(saveProfile.getNumeroTelephone()))
                //.andExpect(jsonPath("$.data.genre").value(saveProfile.getGenre()))
                .andExpect(jsonPath("$.data.addresse").value(saveProfile.getAddresse()))
                .andExpect(jsonPath("$.data.ville").value(saveProfile.getVille()))
                .andExpect(jsonPath("$.data.codePostal").value(saveProfile.getCodePostal()))
                .andExpect(jsonPath("$.data.pays").value(saveProfile.getPays()))
                .andExpect(jsonPath("$.data.profilePicturePrl").value(saveProfile.getProfilePictureUrl()))
                .andExpect(jsonPath("$.data.bio").value(saveProfile.getBio())

                );
    }

    @Test
    void updateProfileByIdNotFound()throws Exception {
        //profile dto
        ProfileDto profileDto =   new ProfileDto(
                19L,
                "Doe",
                "John",
                "1234567890",
                "2022-09-05",
                GenreEnum.male,
                "123 Main St",
                "Paris",
                "75001",
                "France",
                "http://example.com/johndoe.jpg",
                "Bio of John Doe"
        );
        //stranform to json
        String jsonProfile = objectMapper.writeValueAsString(profileDto);

        //Given
        doThrow(new ObjectNotFoundException("profile",19L)).when(this.profileService).update(Mockito.any(Profile.class),eq(19L));
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.patch(url+"/user/profiles/{profileId}", 19L)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonProfile)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.message").value("Nous ne retrouvons pas l'entité profile avec id 19"))
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.data",Matchers.nullValue())
        );
    }



    @Test
    void deleteUserByIdSuccess() throws Exception {
        //Given
        doNothing().when(this.profileService).deleteById(12L);
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.delete(url+"/user/profiles/{profileId}", 12L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("profile supprimé"))
                .andExpect(jsonPath("$.data", Matchers.nullValue())
                );
    }
    @Test
    void deleteUserByIdNotFound() throws Exception {
        //Given
        doThrow(new ObjectNotFoundException("profile",100L)).when(this.profileService).deleteById(100L);
        //When and Then
        mockMvc.perform(MockMvcRequestBuilders.delete(url+"/user/profiles/{profileId}", 100L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.message").value("Nous ne retrouvons pas l'entité profile avec id 100"))
                .andExpect(jsonPath("$.data", Matchers.nullValue())
        );
    }
}