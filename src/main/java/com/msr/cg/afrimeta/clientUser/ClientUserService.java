package com.msr.cg.afrimeta.clientUser;

import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import com.msr.cg.afrimeta.utils.AfrimetaCrudInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientUserService implements AfrimetaCrudInterface<ClientUser> {
    private final ClientUserRepository clientUserRepository;

    public ClientUserService(ClientUserRepository clientUserRepository) {
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
        return this.clientUserRepository.save(clientUser);
    }

    @Override
    public ClientUser update(ClientUser clientUser, Long id) {
        return this.clientUserRepository.findById(id)
                .map(oldClient -> {
                    oldClient.setAdresse(clientUser.getAdresse());
                    oldClient.setNom(clientUser.getNom());
                    oldClient.setPrenom(clientUser.getPrenom());
                    oldClient.setEmail(clientUser.getEmail());
                    oldClient.setTelephone(clientUser.getTelephone());
                    oldClient.setPassword(clientUser.getPassword());
                    oldClient.setRole(clientUser.getRole());
                    oldClient.setEnable(clientUser.isEnable());
                    oldClient.setRaisonSocial(clientUser.getRaisonSocial());
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
}
