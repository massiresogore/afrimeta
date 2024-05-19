package com.msr.cg.afrimeta.clientUser.converter;

import com.msr.cg.afrimeta.clientUser.ClientUser;
import com.msr.cg.afrimeta.clientUser.dto.ClientUserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ClientUserDtoToClientUserConverter implements Converter<ClientUserDto, ClientUser> {

    @Override
    public ClientUser convert(ClientUserDto source) {
        ClientUser clientUser = new ClientUser();
        clientUser.setUser_id(source.user_id());
        clientUser.setAdresse(source.adresse());
        clientUser.setNom(source.nom());
        clientUser.setPrenom(source.prenom());
        clientUser.setEmail(source.email());
        clientUser.setTelephone(source.telephone());
        clientUser.setPassword(source.password());
        clientUser.setRole(source.role());
        clientUser.setEnable(source.enable());
        clientUser.setRaisonSocial(source.raisonSocial());
        return clientUser;
    }
}
