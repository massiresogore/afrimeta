package com.msr.cg.afrimeta.magasin.converter;

import com.msr.cg.afrimeta.clientUser.ClientUserService;
import com.msr.cg.afrimeta.magasin.Magasin;
import com.msr.cg.afrimeta.magasin.dto.MagasinDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MagasinToMagasinDtoConverter implements Converter<Magasin, MagasinDto> {

    private final ClientUserService clientUserService;

    public MagasinToMagasinDtoConverter(ClientUserService clientUserService) {
        this.clientUserService = clientUserService;
    }

    @Override
    public MagasinDto convert(Magasin source) {
       return new MagasinDto(
                source.getMagasinId(),
                source.getLibele(),
                source.getLogoUrl(),
                source.getDescription(),
               Math.toIntExact(this.clientUserService.findById(source.getClientUser().getUser_id()).getUser_id())
        );
    }
}
