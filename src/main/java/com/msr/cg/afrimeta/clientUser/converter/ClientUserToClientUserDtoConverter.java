package com.msr.cg.afrimeta.clientUser.converter;

import com.msr.cg.afrimeta.clientUser.ClientUser;
import com.msr.cg.afrimeta.clientUser.dto.ClientUserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ClientUserToClientUserDtoConverter implements Converter<ClientUser, ClientUserDto> {
    @Override
    public ClientUserDto convert(ClientUser source) {
        return new ClientUserDto(
                source.getUser_id(),
                source.getUsername(),
                source.getEmail(),
                //source.getPassword(),
                source.isEnable(),
                source.getRole(),
                source.getProfile()
        );
    }
}
