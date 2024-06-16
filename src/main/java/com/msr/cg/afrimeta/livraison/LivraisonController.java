package com.msr.cg.afrimeta.livraison;


import com.msr.cg.afrimeta.livraison.converter.LivraisonDtoToLivraisonConverter;
import com.msr.cg.afrimeta.livraison.converter.LivraisonRequestToLivraison;
import com.msr.cg.afrimeta.livraison.converter.LivraisonToLivraisonDtoConverter;
import com.msr.cg.afrimeta.livraison.dto.LivraisonDto;
import com.msr.cg.afrimeta.livraison.dto.LivraisonRequest;
import com.msr.cg.afrimeta.system.Result;
import com.msr.cg.afrimeta.system.StatusCode;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.endpoint.base-url}/livraisons")
public class LivraisonController {

    private final LivraisonService livraisonService;
    private final LivraisonDtoToLivraisonConverter livraisonDtoToLivraisonConverter;
    private final LivraisonToLivraisonDtoConverter livraisonToLivraisonDtoConverter;
    private  final LivraisonRequestToLivraison livraisonRequestToLivraison;

    public LivraisonController(LivraisonService livraisonService, LivraisonDtoToLivraisonConverter livraisonDtoToLivraisonConverter, LivraisonToLivraisonDtoConverter livraisonToLivraisonDtoConverter, LivraisonRequestToLivraison livraisonRequestToLivraison) {
        this.livraisonService = livraisonService;
        this.livraisonDtoToLivraisonConverter = livraisonDtoToLivraisonConverter;
        this.livraisonToLivraisonDtoConverter = livraisonToLivraisonDtoConverter;
        this.livraisonRequestToLivraison = livraisonRequestToLivraison;
    }

    @GetMapping
    public Result getAll()
    {
        List<Livraison> livraisons = livraisonService.findAll();
       return new Result(true,StatusCode.SUCCESS,"Tous les livraisons",this.livraisonToLivraisonDtoConverter.convert(livraisons));
    }

    @GetMapping("/{livraisonId}")
    public Result getById(@PathVariable("livraisonId") String livraisonId){
        Livraison livraison = this.livraisonService.findById(Long.parseLong(livraisonId));
        return new Result(true,StatusCode.SUCCESS,"livraison retrouvé",this.livraisonToLivraisonDtoConverter.convert(livraison));
    }



    @DeleteMapping("/{livraisonId}")
    public Result deleteMagasin(@PathVariable("livraisonId") String livraisonId){
        this.livraisonService.deleteById(Long.parseLong(livraisonId));
         return new Result(true,StatusCode.SUCCESS,"livraison annulé");
    }

    @PostMapping("modelivraison/{modelivraisonId}/commande/{commandeId}")
    public Result save(@RequestBody LivraisonDto livraisonDto, @PathVariable("modelivraisonId") String modeLivraisonId, @PathVariable("commandeId") String commandeId) {

       //Convert to livraison
        Livraison livraisonTobeSave = this.livraisonDtoToLivraisonConverter.convert(livraisonDto,modeLivraisonId,commandeId);
       // System.out.println(livraisonTobeSave);*/
        //Update
        Livraison livraisonSaved = this.livraisonService.save(livraisonTobeSave);
//
//        //Convert to dto
        LivraisonDto livraisonDtoresponse = this.livraisonToLivraisonDtoConverter.convert(livraisonSaved);

        return new Result(true,StatusCode.SUCCESS,"Livraison crée",livraisonDtoresponse);
    }

    @PatchMapping("/{livraisonId}")
    public Result update( LivraisonRequest livraisonRequest){

        //request to paiment
        Livraison livraison = this.livraisonRequestToLivraison.convert(livraisonRequest);
        //update livraison
       Livraison updatedLivraison =  this.livraisonService.update(livraison,Long.parseLong(livraisonRequest.livraisonId()));
       //Livraison to livraison dto
        LivraisonDto livraisonDto =this.livraisonToLivraisonDtoConverter.convert(updatedLivraison);

        return new Result(true,StatusCode.SUCCESS,"Livraison mis à jour",livraisonDto);
    }


    }
