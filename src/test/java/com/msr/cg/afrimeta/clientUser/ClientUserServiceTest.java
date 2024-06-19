package com.msr.cg.afrimeta.clientUser;

import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    @Mock
    private PasswordEncoder passwordEncoder;

    List<ClientUser> clientUsers;

    @BeforeEach
    void setUp() {

        clientUsers = new ArrayList<>();
        ClientUser clientUser = new ClientUser();

        clientUser.setEmail("m@gmail.com");
        clientUser.setPassword("MZMZMZMZMZMZZM");
        clientUser.setRole("ADMIN USER");
        clientUser.setEnable(true);

        ClientUser clientUser1 = new ClientUser();
        clientUser1.setEmail("m@gmail.com");
        clientUser1.setPassword("MZMZMZMZMZMZZM");
        clientUser1.setRole("ADMIN USER");
        clientUser1.setEnable(true);

        ClientUser clientUser2 = new ClientUser();
        clientUser2.setEmail("m@gmail.com");
        clientUser2.setPassword("MZMZMZMZMZMZZM");
        clientUser2.setRole("ADMIN USER");
        clientUser2.setEnable(true);


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
        clientUsers = new ArrayList<>();
        ClientUser clientUser = new ClientUser();
        clientUser.setEmail("m@gmail.com");
        clientUser.setPassword("MZMZMZMZMZMZZM");
        clientUser.setRole("ADMIN USER");
        clientUser.setEnable(true);

        given(this.clientUserRepository.findById(1L)).willReturn(Optional.of(clientUser));
        ClientUser clientUser1 = clientUserService.findById(1L);
        assertThat(clientUser1.getEmail()).isEqualTo("m@gmail.com");
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
        clientUsers = new ArrayList<>();
        ClientUser clientUser = new ClientUser();
        clientUser.setEmail("m@gmail.com");
        clientUser.setPassword("MZMZMZMZMZMZZM");
        clientUser.setRole("ADMIN USER");
        clientUser.setEnable(true);

        given(this.clientUserRepository.save(clientUser)).willReturn(clientUser);

        ClientUser clientUser1 = clientUserService.save(clientUser);
        assertThat(clientUser1.getEmail()).isEqualTo(clientUser.getEmail());
        verify(this.clientUserRepository, times(1)).save(clientUser);
    }

    @Test
    void updateSuccess() {
        ClientUser clientUser = new ClientUser();
        clientUser.setUser_id(1L);

        clientUser.setEmail("m@gmail.com");
        clientUser.setPassword("MZMZMZMZMZMZZM");
        clientUser.setRole("ADMIN USER");
        clientUser.setEnable(true);

        ClientUser update = new ClientUser();
        clientUser.setUser_id(1L);

        update.setEmail("m@gmail.com");
        update.setPassword("MZMZMZMZMZMZZM");
        update.setRole("ADMIN USER");
        update.setEnable(true);

        given(this.clientUserRepository.findById(1L)).willReturn(Optional.of(clientUser));
        given(this.clientUserRepository.save(clientUser)).willReturn(clientUser);

        ClientUser updated = this.clientUserService.update(update,1L);
        assertThat(updated.getEmail()).isEqualTo(update.getEmail());
        verify(this.clientUserRepository, times(1)).findById(1L);
        verify(this.clientUserRepository, times(1)).save(clientUser);
    }

    @Test
    void deleteById() {
        ClientUser clientUser = new ClientUser();
        clientUser.setUser_id(1L);
        clientUser.setEmail("m@gmail.com");
        clientUser.setPassword("MZMZMZMZMZMZZM");
        clientUser.setRole("ADMIN USER");
        clientUser.setEnable(true);

        given(this.clientUserRepository.findById(1L)).willReturn(Optional.of(clientUser));
        doNothing().when(this.clientUserRepository).deleteById(1L);

        this.clientUserService.deleteById(1L);

        verify(this.clientUserRepository, times(1)).deleteById(1L);
    }
     @Test
    void deleteByIdNotFound() {
        given(this.clientUserRepository.findById(13L)).willReturn(Optional.empty());
        Throwable throwable = catchThrowable(() -> this.clientUserService.deleteById(13L));
        assertThat(throwable)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Nous ne retrouvons pas l'entité clientUser avec id 13");

        verify(this.clientUserRepository, times(1)).findById(13L);
    }

}