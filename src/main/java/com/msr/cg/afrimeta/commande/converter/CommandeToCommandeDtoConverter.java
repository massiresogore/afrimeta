package com.msr.cg.afrimeta.commande.converter;

import com.msr.cg.afrimeta.clientUser.converter.ClientUserToClientUserDtoConverter;
import com.msr.cg.afrimeta.commande.Commande;
import com.msr.cg.afrimeta.commande.dto.CommandeDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class CommandeToCommandeDtoConverter implements Converter<Commande, CommandeDto> {
    private final ClientUserToClientUserDtoConverter clientUserToClientUserDtoConverter;

    public CommandeToCommandeDtoConverter(ClientUserToClientUserDtoConverter clientUserToClientUserDtoConverter ) {
        this.clientUserToClientUserDtoConverter = clientUserToClientUserDtoConverter;
    }

    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public CommandeDto convert(Commande source) {
        return new CommandeDto(
                source.getCommandeId(),
                source.getCreatedAt().format(EuropeanDateTimeFormatter()),
                source.getUpdatedAt().format(EuropeanDateTimeFormatter()),
                source.getCommandeTotal(),
                source.getAdresse(),
                source.getPrixTotal(),
                source.getNombreProduit(),
                this.clientUserToClientUserDtoConverter.convert(source.getClientUser())   );
    }

    public List<CommandeDto> convert(List<Commande> source) {
       return   source.stream().map(commande ->
            new CommandeDto(
                    commande.getCommandeId(),
                    commande.getCreatedAt().format(EuropeanDateTimeFormatter()),
                    commande.getUpdatedAt().format(EuropeanDateTimeFormatter()),
                    commande.getCommandeTotal(),
                    commande.getAdresse(),
                    commande.getPrixTotal(),
                    commande.getNombreProduit(),
                    this.clientUserToClientUserDtoConverter.convert(commande.getClientUser()))
        ).toList();
    }

    static DateTimeFormatter EuropeanDateTimeFormatter() {
        return DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    }


}
