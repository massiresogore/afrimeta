package com.msr.cg.afrimeta.profile;

import com.msr.cg.afrimeta.clientUser.ClientUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @Mock
    ProfileRepository profileRepository;

    @MockBean
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
        assertThat(retrivedProfiles).hasSize(3);
        verify(this.profileRepository, times(1)).findAll();
    }

    @Test
    void findById() {
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void delete() {
    }
}