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
        clientUser.setEmail(source.email());
       // clientUser.setPassword(source.password());
        clientUser.setRole(source.role());
        clientUser.setEnable(source.enable());
        clientUser.setUsername(source.username());
        clientUser.setProfile(source.profile());
        return clientUser;
    }
}
