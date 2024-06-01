package com.msr.cg.afrimeta.clientUser.converter;

import com.msr.cg.afrimeta.clientUser.ClientUser;
import com.msr.cg.afrimeta.clientUser.dto.ClientUserRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ClientUserRequestToClientUserConverter implements Converter<ClientUserRequest, ClientUser> {

    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public ClientUser convert(ClientUserRequest source) {
       return new ClientUser(
            source.user_id(),
            source.username(),
            source.email(),
            source.password(),
            true,
            "USER",
            source.profile()
       );
    }
}