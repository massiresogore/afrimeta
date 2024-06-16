package com.msr.cg.afrimeta.commentaire.converter;


import com.msr.cg.afrimeta.clientUser.converter.ClientUserToClientUserDtoConverter;
import com.msr.cg.afrimeta.commentaire.Commentaire;
import com.msr.cg.afrimeta.commentaire.dto.CommentaireDto;
import com.msr.cg.afrimeta.produit.dto.converter.ProduitToProduitDtoConverter;
import com.msr.cg.afrimeta.utils.AfrimetaUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentaireToCommentaireDtoConverter implements Converter<Commentaire, CommentaireDto> {

    private final ProduitToProduitDtoConverter produitToProduitDtoConverter;
    private final ClientUserToClientUserDtoConverter clientUserToClientUserDtoConverter;

    public CommentaireToCommentaireDtoConverter(ProduitToProduitDtoConverter produitToProduitDtoConverter, ClientUserToClientUserDtoConverter clientUserToClientUserDtoConverter) {
        this.produitToProduitDtoConverter = produitToProduitDtoConverter;
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
    public CommentaireDto convert(Commentaire source) {

        return new CommentaireDto(
                source.getPaimementId(),
                source.getCommentaireDate().format(AfrimetaUtils.EuropeanDateTimeFormatter()),
                source.getContenu(),
                this.produitToProduitDtoConverter.convert(source.getProduit()),
                this.clientUserToClientUserDtoConverter.convert(source.getClientUser())

        );
    }

    public List<CommentaireDto> convert(List<Commentaire> sources) {

       return sources.stream().map(source-> new CommentaireDto(
                 source.getPaimementId(),
                 source.getCommentaireDate().format(AfrimetaUtils.EuropeanDateTimeFormatter()),
                 source.getContenu(),
               this.produitToProduitDtoConverter.convert(source.getProduit()),
               this.clientUserToClientUserDtoConverter.convert(source.getClientUser())
       )).toList();
    }
}
