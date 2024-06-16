package com.msr.cg.afrimeta.commentaire.converter;

import com.msr.cg.afrimeta.clientUser.ClientUser;
import com.msr.cg.afrimeta.clientUser.ClientUserService;
import com.msr.cg.afrimeta.produit.Produit;
import com.msr.cg.afrimeta.produit.ProduitService;
import com.msr.cg.afrimeta.commentaire.Commentaire;
import com.msr.cg.afrimeta.commentaire.dto.CommentaireRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CommentaireRequestToCommentaire implements Converter<CommentaireRequest, Commentaire> {

    private final ProduitService produitService;
    private final ClientUserService clientUserService;

    public CommentaireRequestToCommentaire(ProduitService produitService, ClientUserService clientUserService) {
        this.produitService = produitService;
        this.clientUserService = clientUserService;
    }


    /**
     * Convert the source object of type {@code S} to target type {@code T}.
     *
     * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public Commentaire convert(CommentaireRequest source) {
        Produit produit = this.produitService.findById(Long.parseLong(source.produitId()));
        ClientUser clientUser = this.clientUserService.findById(Long.parseLong(source.clientUserId()));
        return new Commentaire(LocalDateTime.now(), source.description(),clientUser,produit);

    }
}
