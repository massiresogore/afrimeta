package com.msr.cg.afrimeta.profile;

import com.msr.cg.afrimeta.clientUser.ClientUser;
import com.msr.cg.afrimeta.profile.dto.ProfileDto;
import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @Mock
    ProfileRepository profileRepository;

    @InjectMocks
    ProfileService profileService;

    List<Profile> profiles = new ArrayList<>();

    @BeforeEach
    void setUp() {

        Profile profile1 = new Profile(1L,  "Doe", "John", "1234567890",new Date(1290), GenreEnum.male, "123 Main St", "Paris", "75001", "France", "http://example.com/johndoe.jpg", "Bio of John Doe");
        Profile profile2 = new Profile(2L,  "Smith", "Jane", "0987654321", new Date(2024), GenreEnum.female, "456 Elm St", "Lyon", "69001", "France", "http://example.com/janesmith.jpg", "Bio of Jane Smith");
        Profile profile3 = new Profile(3L,  "Jones", "Alice", "1122334455", new Date(2022), GenreEnum.female, "789 Oak St", "Marseille", "13001", "France", "http://example.com/alicejones.jpg", "Bio of Alice Jones");
        Profile profile4 = new Profile(4L,  "Brown", "Bob", "2233445566", new Date(2021), GenreEnum.female, "101 Pine St", "Nice", "06000", "France", "http://example.com/bobbrown.jpg", "Bio of Bob Brown");
        Profile profile5 = new Profile(5L,  "White", "Carol", "3344556677",new Date(2024), GenreEnum.female, "202 Birch St", "Toulouse", "31000", "France", "http://example.com/carolwhite.jpg", "Bio of Carol White");

        profiles.add(profile1);
        profiles.add(profile2);
        profiles.add(profile3);
        profiles.add(profile4);
        profiles.add(profile5);
    }

    @Test
    void findAll() {
        //Given
        given(this.profileRepository.findAll()).willReturn(profiles);
        //When
        List<Profile> retrivedProfiles = this.profileService.findAll();
        //Then
        assertThat(retrivedProfiles).hasSize(5);
        assertThat(retrivedProfiles.size()).isEqualTo(this.profiles.size());
        verify(this.profileRepository, times(1)).findAll();
    }

    @Test
    void findById() {
        Profile profile = new Profile(1L,
                "Doe",
                "John",
                "1234567890",
                new Date(1290),
                GenreEnum.male,
                "123 Main St",
                "Paris",
                "75001",
                "France",
                "http://example.com/johndoe.jpg",
                "Bio of John Doe"
        );
        //Given
        given(this.profileRepository.findById(1L)).willReturn(Optional.of(profile));
        //When
        Profile retrievedProfile = this.profileService.findById(1L);
        //Then
        assertThat(retrievedProfile).isEqualTo(profile);
        assertThat(retrievedProfile.getAddresse()).isEqualTo(profile.getAddresse());
        verify(this.profileRepository, times(1)).findById(1L);
    }

    @Test
    void save() {

     ProfileDto profileDto =   new ProfileDto(
                null,
                "Doe",
                "John",
                "1234567890",
                new Date(1290),
                GenreEnum.male,
                "123 Main St",
                "Paris",
                "75001",
                "France",
                "http://example.com/johndoe.jpg",
                "Bio of John Doe"
        );

      Profile saveProfile=  new Profile(
                profileDto.profileId(),
                profileDto.nom(),
                profileDto.prenom(),
                profileDto.numeroTelephone(),
                profileDto.dateNaissance(),
                profileDto.genre(),
                profileDto.addresse(),
                profileDto.ville(),
                profileDto.codePostal(),
                profileDto.pays(),
                profileDto.profilePicturePrl(),
                profileDto.bio()
        );


      //given
        given(this.profileRepository.save(saveProfile)).willReturn(saveProfile);
        //When
        Profile savedProfile = this.profileService.save(saveProfile);
        // then
        assertThat(savedProfile).isEqualTo(saveProfile);
        assertThat(savedProfile.getAddresse()).isEqualTo(saveProfile.getAddresse());
        assertThat(savedProfile.getNom()).isEqualTo(saveProfile.getNom());
        assertThat(savedProfile.getPrenom()).isEqualTo(saveProfile.getPrenom());
        assertThat(savedProfile.getNumeroTelephone()).isEqualTo(saveProfile.getNumeroTelephone());
        assertThat(savedProfile.getDateNaissance()).isEqualTo(saveProfile.getDateNaissance());
        verify(this.profileRepository, times(1)).save(saveProfile);


    }

    @Test
    void update() {
        ProfileDto profileDto =   new ProfileDto(
                1L,
                "Doe updaded",
                "John update",
                "1234567890",
                new Date(1980),
                GenreEnum.male,
                "123 Main St",
                "Paris",
                "75001",
                "France",
                "http://example.com/johndoe.jpg",
                "Bio of John Doe"
        );
        Profile saveProfile=  new Profile(
                profileDto.profileId(),
                profileDto.nom(),
                profileDto.prenom(),
                profileDto.numeroTelephone(),
                profileDto.dateNaissance(),
                profileDto.genre(),
                profileDto.addresse(),
                profileDto.ville(),
                profileDto.codePostal(),
                profileDto.pays(),
                profileDto.profilePicturePrl(),
                profileDto.bio()
        );

        //Given
        given(this.profileRepository.findById(1L)).willReturn(Optional.of(saveProfile));
        given(this.profileRepository.save(saveProfile)).willReturn(saveProfile);

        //When
        Profile savedProfile = this.profileService.update(saveProfile,1L);

        //Then
        assertThat(savedProfile).isEqualTo(saveProfile);
        assertThat(savedProfile.getProfileId()).isEqualTo(saveProfile.getProfileId());
        assertThat(savedProfile.getAddresse()).isEqualTo(saveProfile.getAddresse());
        assertThat(savedProfile.getNom()).isEqualTo(saveProfile.getNom());
        verify(this.profileRepository, times(1)).findById(1L);
        verify(this.profileRepository, times(1)).save(saveProfile);
    }

    @Test
    void updateByIdNotFound() {
        //Given
        given(this.profileRepository.findById(13L)).willReturn(Optional.empty());
        //When
        Throwable throwable = catchThrowable(() -> this.profileService.deleteById(13L));
        //then
        assertThat(throwable)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Nous ne retrouvons pas l'entité profile avec id 13");
        verify(this.profileRepository, times(1)).findById(13L);

    }

    @Test
    void deleteById() {
        Profile profile = new Profile(
                1L,
                "Doe",
                "John",
                "1234567890",
                new Date(1290),
                GenreEnum.male,
                "123 Main St",
                "Paris",
                "75001",
                "France",
                "http://example.com/johndoe.jpg",
                "Bio of John Doe"
        );
        //Given
        given(this.profileRepository.findById(1L)).willReturn(Optional.of(profile));
        doNothing().when(this.profileRepository).deleteById(1L);
        //When
        this.profileService.deleteById(1L);
        //Then
        verify(this.profileRepository, times(1)).findById(1L);
    }

    @Test
    void deleteByIdNotFound() {

        //Given
        given(this.profileRepository.findById(12L)).willReturn(Optional.empty());
        //when
        Throwable throwable = catchThrowable(()->this.profileService.deleteById(12L));
        //Then
        assertThat(throwable)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Nous ne retrouvons pas l'entité profile avec id 12");

        //Then
       verify(this.profileRepository, times(1)).findById(12L);

    }


}