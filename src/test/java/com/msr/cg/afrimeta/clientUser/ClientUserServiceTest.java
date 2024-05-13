package com.msr.cg.afrimeta.clientUser;

import com.msr.cg.afrimeta.adresse.Adresse;
import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import com.msr.cg.afrimeta.ville.Ville;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ClientUserServiceTest {
    @Mock
    private ClientUserRepository clientUserRepository;
    @InjectMocks
    private ClientUserService clientUserService;

    List<ClientUser> clientUsers;

    @BeforeEach
    void setUp() {
        Ville paris = new Ville("Paris");
        Adresse adresse = new Adresse("77440",77440,paris);

        clientUsers = new ArrayList<>();
        ClientUser clientUser = new ClientUser();
        clientUser.setAdresse(adresse);
        clientUser.setNom("Lolo");
        clientUser.setPrenom("Ebemba");
        clientUser.setEmail("m@gmail.com");
        clientUser.setTelephone("0909090");
        clientUser.setPassword("MZMZMZMZMZMZZM");
        clientUser.setRole("ADMIN USER");
        clientUser.setEnable(true);
        clientUser.setRaisonSocial("Amazon");

        ClientUser clientUser1 = new ClientUser();
        clientUser1.setAdresse(adresse);
        clientUser1.setNom("Lolo");
        clientUser1.setPrenom("Ebemba");
        clientUser1.setEmail("m@gmail.com");
        clientUser1.setTelephone("0909090");
        clientUser1.setPassword("MZMZMZMZMZMZZM");
        clientUser1.setRole("ADMIN USER");
        clientUser1.setEnable(true);
        clientUser1.setRaisonSocial("Amazon");

        ClientUser clientUser2 = new ClientUser();
        clientUser2.setAdresse(adresse);
        clientUser2.setNom("Lolo");
        clientUser2.setPrenom("Ebemba");
        clientUser2.setEmail("m@gmail.com");
        clientUser2.setTelephone("0909090");
        clientUser2.setPassword("MZMZMZMZMZMZZM");
        clientUser2.setRole("ADMIN USER");
        clientUser2.setEnable(true);
        clientUser2.setRaisonSocial("Amazon");


        clientUsers.add(clientUser);
        clientUsers.add(clientUser1);
        clientUsers.add(clientUser2);
    }

    @Test
    void findAll() {
        given(this.clientUserRepository.findAll()).willReturn(clientUsers);
        List<ClientUser> clientUsers = clientUserService.findAll();
        assertThat(this.clientUsers.size()).isEqualTo(clientUsers.size());
        verify(this.clientUserRepository, times(1)).findAll();
    }

    @Test
    void findById() {
        Ville paris = new Ville("Paris");
        Adresse adresse = new Adresse("77440",77440,paris);

        clientUsers = new ArrayList<>();
        ClientUser clientUser = new ClientUser();
        clientUser.setAdresse(adresse);
        clientUser.setNom("Lolo");
        clientUser.setPrenom("Ebemba");
        clientUser.setEmail("m@gmail.com");
        clientUser.setTelephone("0909090");
        clientUser.setPassword("MZMZMZMZMZMZZM");
        clientUser.setRole("ADMIN USER");
        clientUser.setEnable(true);
        clientUser.setRaisonSocial("Amazon");

        given(this.clientUserRepository.findById(1L)).willReturn(Optional.of(clientUser));
        ClientUser clientUser1 = clientUserService.findById(1L);
        assertThat(clientUser1.getAdresse()).isEqualTo(adresse);
        assertThat(clientUser1.getNom()).isEqualTo("Lolo");
        assertThat(clientUser1.getPrenom()).isEqualTo("Ebemba");
        assertThat(clientUser1.getEmail()).isEqualTo("m@gmail.com");
        assertThat(clientUser1.getTelephone()).isEqualTo("0909090");
        assertThat(clientUser1.getPassword()).isEqualTo("MZMZMZMZMZMZZM");
        assertThat(clientUser1.getRole()).isEqualTo("ADMIN USER");
        verify(this.clientUserRepository, times(1)).findById(1L);
    }

    @Test
    void findByIdNotFound() {
        given(this.clientUserRepository.findById(1L)).willReturn(Optional.empty());
        Throwable throwable = catchThrowable(()->clientUserService.findById(1L));
        assertThat(throwable)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Nous ne retrouvons pas l'entité clientUser avec id 1");
        verify(this.clientUserRepository, times(1)).findById(1L);
    }

    @Test
    void save() {
        Ville paris = new Ville("Paris");
        Adresse adresse = new Adresse("77440",77440,paris);

        clientUsers = new ArrayList<>();
        ClientUser clientUser = new ClientUser();
        clientUser.setAdresse(adresse);
        clientUser.setNom("Lolo");
        clientUser.setPrenom("Ebemba");
        clientUser.setEmail("m@gmail.com");
        clientUser.setTelephone("0909090");
        clientUser.setPassword("MZMZMZMZMZMZZM");
        clientUser.setRole("ADMIN USER");
        clientUser.setEnable(true);
        clientUser.setRaisonSocial("Amazon");

        given(this.clientUserRepository.save(clientUser)).willReturn(clientUser);

        ClientUser clientUser1 = clientUserService.save(clientUser);
        assertThat(clientUser1.getAdresse()).isEqualTo(adresse);
        assertThat(clientUser1.getNom()).isEqualTo("Lolo");
        assertThat(clientUser1.getPrenom()).isEqualTo("Ebemba");
        verify(this.clientUserRepository, times(1)).save(clientUser);
    }

    @Test
    void updateSuccess() {
        Ville paris = new Ville("Paris");
        Adresse adresse = new Adresse("77440",77440,paris);

        ClientUser clientUser = new ClientUser();
        clientUser.setUser_id(1L);
        clientUser.setAdresse(adresse);
        clientUser.setNom("Lolo");
        clientUser.setPrenom("Ebemba");
        clientUser.setEmail("m@gmail.com");
        clientUser.setTelephone("0909090");
        clientUser.setPassword("MZMZMZMZMZMZZM");
        clientUser.setRole("ADMIN USER");
        clientUser.setEnable(true);
        clientUser.setRaisonSocial("Amazon");

        ClientUser update = new ClientUser();
        clientUser.setUser_id(1L);
        update.setAdresse(adresse);
        update.setNom("Lolo");
        update.setPrenom("Ebemba");
        update.setEmail("m@gmail.com");
        update.setTelephone("0909090");
        update.setPassword("MZMZMZMZMZMZZM");
        update.setRole("ADMIN USER");
        update.setEnable(true);
        update.setRaisonSocial("Amazon");

        given(this.clientUserRepository.findById(1L)).willReturn(Optional.of(clientUser));
        given(this.clientUserRepository.save(clientUser)).willReturn(clientUser);

        ClientUser updated = this.clientUserService.update(update,1L);
        assertThat(updated.getAdresse()).isEqualTo(update.getAdresse());
        assertThat(updated.getNom()).isEqualTo(update.getNom());
        assertThat(updated.getPrenom()).isEqualTo(update.getPrenom());
        assertThat(updated.getEmail()).isEqualTo(update.getEmail());
        assertThat(updated.getTelephone()).isEqualTo(update.getTelephone());

        verify(this.clientUserRepository, times(1)).findById(1L);
        verify(this.clientUserRepository, times(1)).save(clientUser);
    }

    @Test
    void deleteById() {
        Ville paris = new Ville("Paris");
        Adresse adresse = new Adresse("77440",77440,paris);

        ClientUser clientUser = new ClientUser();
        clientUser.setUser_id(1L);
        clientUser.setAdresse(adresse);
        clientUser.setNom("Lolo");
        clientUser.setPrenom("Ebemba");
        clientUser.setEmail("m@gmail.com");
        clientUser.setTelephone("0909090");
        clientUser.setPassword("MZMZMZMZMZMZZM");
        clientUser.setRole("ADMIN USER");
        clientUser.setEnable(true);
        clientUser.setRaisonSocial("Amazon");

        given(this.clientUserRepository.findById(1L)).willReturn(Optional.of(clientUser));
        doNothing().when(this.clientUserRepository).deleteById(1L);

        this.clientUserService.deleteById(1L);

        verify(this.clientUserRepository, times(1)).deleteById(1L);
    }
     @Test
    void deleteByIdNotFound() {
        given(this.clientUserRepository.findById(Mockito.anyLong())).willReturn(Optional.empty());

        Throwable throwable = catchThrowable(() -> this.clientUserService.deleteById(13L));
        assertThat(throwable)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Nous ne retrouvons pas l'entité clientUser avec id 13");

        verify(this.clientUserRepository, times(1)).findById(13L);
    }

}