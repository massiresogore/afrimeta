package com.msr.cg.afrimeta.clientUser;

import com.msr.cg.afrimeta.security.MyUserPrincipal;
import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import com.msr.cg.afrimeta.utils.AfrimetaCrudInterface;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientUserService implements AfrimetaCrudInterface<ClientUser> , UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final ClientUserRepository clientUserRepository;

    public ClientUserService(PasswordEncoder passwordEncoder, ClientUserRepository clientUserRepository) {
        this.passwordEncoder = passwordEncoder;
        this.clientUserRepository = clientUserRepository;
    }


    @Override
    public List<ClientUser> findAll() {
      return (List<ClientUser>) this.clientUserRepository.findAll();
    }


    @Override
    public ClientUser findById(Long id) {
        return this.clientUserRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("clientUser",id));
    }

    @Override
    public ClientUser save(ClientUser clientUser) {
        clientUser.setPassword(passwordEncoder.encode(clientUser.getPassword()));
        clientUser.setEnable(true);
        return this.clientUserRepository.save(clientUser);
    }

    @Override
    public ClientUser update(ClientUser clientUser, Long id) {
        return this.clientUserRepository.findById(id)
                .map(oldClient -> {
                    oldClient.setEmail(clientUser.getEmail());
                    oldClient.setPassword(clientUser.getPassword());
                    oldClient.setRole(clientUser.getRole());
                    oldClient.setEnable(clientUser.isEnable());
                    return this.clientUserRepository.save(oldClient);
                })
                .orElseThrow(()-> new ObjectNotFoundException("clientUser",id));
    }

    @Override
    public void deleteById(Long id) {
        this.findById(id);
        this.clientUserRepository.deleteById(id);
    }

    @Override
    public void delete(ClientUser clientUser) {
        this.clientUserRepository.delete(clientUser);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return   this.clientUserRepository.findByUsername(username)
                .map(clientUser -> new MyUserPrincipal(clientUser))
                .orElseThrow(()-> new UsernameNotFoundException("username "+username+" is not found"));
    }
}
