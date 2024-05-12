package com.msr.cg.afrimeta.clientUser.converter;

import com.msr.cg.afrimeta.clientUser.ClientUser;
import com.msr.cg.afrimeta.clientUser.dto.ClientUserDto;
import org.springframework.core.convert.converter.Converter;

public class ClientUserDtoToClientUserConverter implements Converter<ClientUserDto, ClientUser> {
    @Override
    public ClientUser convert(ClientUserDto source) {
        ClientUser clientUser = new ClientUser();
        clientUser.setUser_id(source.user_id());
       // clientUser.set
        return clientUser;
    }
}
