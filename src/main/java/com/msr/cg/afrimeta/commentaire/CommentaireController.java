package com.msr.cg.afrimeta.commentaire;


import com.msr.cg.afrimeta.commentaire.converter.CommentaireDtoToCommentaireConverter;
import com.msr.cg.afrimeta.commentaire.converter.CommentaireRequestToCommentaire;
import com.msr.cg.afrimeta.commentaire.converter.CommentaireToCommentaireDtoConverter;
import com.msr.cg.afrimeta.commentaire.dto.CommentaireDto;
import com.msr.cg.afrimeta.commentaire.dto.CommentaireRequest;
import com.msr.cg.afrimeta.system.Result;
import com.msr.cg.afrimeta.system.StatusCode;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.endpoint.base-url}/commentaires")
public class CommentaireController {

    private final CommentaireService commentaireService;
    private final CommentaireDtoToCommentaireConverter commentaireDtoToCommentaireConverter;
    private final CommentaireToCommentaireDtoConverter commentaireToCommentaireDtoConverter;
    private  final CommentaireRequestToCommentaire commentaireRequestToCommentaire;

    public CommentaireController(CommentaireService commentaireService, CommentaireDtoToCommentaireConverter commentaireDtoToCommentaireConverter, CommentaireToCommentaireDtoConverter commentaireToCommentaireDtoConverter, CommentaireRequestToCommentaire commentaireRequestToCommentaire) {
        this.commentaireService = commentaireService;
        this.commentaireDtoToCommentaireConverter = commentaireDtoToCommentaireConverter;
        this.commentaireToCommentaireDtoConverter = commentaireToCommentaireDtoConverter;
        this.commentaireRequestToCommentaire = commentaireRequestToCommentaire;
    }

    @GetMapping
    public Result getAll()
    {
        List<Commentaire> commentaires = commentaireService.findAll();
       return new Result(true,StatusCode.SUCCESS,"Tous les commentaires",this.commentaireToCommentaireDtoConverter.convert(commentaires));
    }

    @GetMapping("/{commentaireId}")
    public Result getById(@PathVariable("commentaireId") String commentaireId){
        Commentaire commentaire = this.commentaireService.findById(Long.parseLong(commentaireId));
        return new Result(true,StatusCode.SUCCESS,"commentaire retrouvé",this.commentaireToCommentaireDtoConverter.convert(commentaire));
    }



    @DeleteMapping("/{commentaireId}")
    public Result deleteMagasin(@PathVariable("commentaireId") String commentaireId){
        this.commentaireService.deleteById(Long.parseLong(commentaireId));
         return new Result(true,StatusCode.SUCCESS,"commentaire annulé");
    }

    @PostMapping("client/{clientuserId}/produit/{produitId}")
    public Result save(@RequestBody CommentaireDto commentaireDto, @PathVariable("clientuserId") String clientuserId, @PathVariable("produitId") String produitId) {

       //Convert to commentaire
        Commentaire commentaireTobeSave = this.commentaireDtoToCommentaireConverter.convert(commentaireDto,clientuserId,produitId);
       // System.out.println(commentaireTobeSave);*/
        //Update
        Commentaire commentaireSaved = this.commentaireService.save(commentaireTobeSave);

//        //Convert to dto
        CommentaireDto commentaireDtoresponse = this.commentaireToCommentaireDtoConverter.convert(commentaireSaved);

        return new Result(true,StatusCode.SUCCESS,"Commentaire crée",commentaireDtoresponse);
    }

    @PatchMapping("/{commentaireId}")
    public Result update( CommentaireRequest commentaireRequest){

        //request to paiment
        Commentaire commentaire = this.commentaireRequestToCommentaire.convert(commentaireRequest);
        //update commentaire
       Commentaire updatedCommentaire =  this.commentaireService.update(commentaire,Long.parseLong(commentaireRequest.commentaireId()));
       //Commentaire to commentaire dto
        CommentaireDto commentaireDto =this.commentaireToCommentaireDtoConverter.convert(updatedCommentaire);

        return new Result(true,StatusCode.SUCCESS,"Commentaire mis à jour",commentaireDto);
    }


    }
