package com.msr.cg.afrimeta.commentaire.converter;

import com.msr.cg.afrimeta.clientUser.ClientUserService;
import com.msr.cg.afrimeta.clientUser.converter.ClientUserDtoToClientUserConverter;
import com.msr.cg.afrimeta.produit.ProduitService;

import com.msr.cg.afrimeta.commentaire.Commentaire;
import com.msr.cg.afrimeta.commentaire.dto.CommentaireDto;
import com.msr.cg.afrimeta.produit.dto.converter.ProduitDtoToProduitConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CommentaireDtoToCommentaireConverter implements Converter<CommentaireDto, Commentaire> {
    private final ClientUserDtoToClientUserConverter clientUserDtoToClientUserConverter;
    private final ProduitDtoToProduitConverter produitDtoToProduitConverter;
    private final ClientUserService clientUserService;
    private  final ProduitService produitService;

    public CommentaireDtoToCommentaireConverter(ClientUserDtoToClientUserConverter clientUserDtoToClientUserConverter, ProduitDtoToProduitConverter produitDtoToProduitConverter, ClientUserService clientUserService, ProduitService produitService) {
        this.clientUserDtoToClientUserConverter = clientUserDtoToClientUserConverter;
        this.produitDtoToProduitConverter = produitDtoToProduitConverter;
        this.clientUserService = clientUserService;
        this.produitService = produitService;
    }

    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public Commentaire convert(CommentaireDto source) {
        return new Commentaire(
                source.commentaireId(),
                LocalDateTime.now(),
                source.contenu(),
                this.clientUserDtoToClientUserConverter.convert(source.clientUserDto()),
                this.produitDtoToProduitConverter.convert(source.produitDto())
        );
    }


  /*  public Commentaire convert(CommentaireRequest source) {
        return new Commentaire(
                source.livraisonId(),
                source.paiementDate(),
                source.description(),
                this.clientUserService.findById(Long.valueOf(source.clientUserId())),
                this.produitService.findById(Long.parseLong(source.produitId()))
        );
    }*/

    public Commentaire convert(CommentaireDto source, String clientUserId, String produitId) {

      return new Commentaire(
              source.commentaireId(),
              LocalDateTime.now(),
              source.contenu(),
              this.clientUserService.findById(Long.valueOf(clientUserId)),
              this.produitService.findById(Long.parseLong(produitId))
        );
    }

}
