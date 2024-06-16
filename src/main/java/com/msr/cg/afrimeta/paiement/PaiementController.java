package com.msr.cg.afrimeta.paiement;

import com.msr.cg.afrimeta.commande.Commande;
import com.msr.cg.afrimeta.commande.dto.CommandeDto;
import com.msr.cg.afrimeta.commande.request.CommandeRequest;
import com.msr.cg.afrimeta.magasin.dto.MagasinDto;
import com.msr.cg.afrimeta.magasin.dto.MagasinRequest;
import com.msr.cg.afrimeta.paiement.converter.PaiementDtoToPaiementConverter;
import com.msr.cg.afrimeta.paiement.converter.PaiementRequestToPaiement;
import com.msr.cg.afrimeta.paiement.converter.PaiementToPaiementDtoConverter;
import com.msr.cg.afrimeta.paiement.dto.PaiementDto;
import com.msr.cg.afrimeta.paiement.dto.PaiementRequest;
import com.msr.cg.afrimeta.system.Result;
import com.msr.cg.afrimeta.system.StatusCode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.endpoint.base-url}/paiements")
public class PaiementController {

    private final PaiementService paiementService;
    private final PaiementDtoToPaiementConverter paiementDtoToPaiementConverter;
    private final PaiementToPaiementDtoConverter paiementToPaiementDtoConverter;
    private  final PaiementRequestToPaiement paiementRequestToPaiement;

    public PaiementController(PaiementService paiementService, PaiementDtoToPaiementConverter paiementDtoToPaiementConverter, PaiementToPaiementDtoConverter paiementToPaiementDtoConverter, PaiementRequestToPaiement paiementRequestToPaiement) {
        this.paiementService = paiementService;
        this.paiementDtoToPaiementConverter = paiementDtoToPaiementConverter;
        this.paiementToPaiementDtoConverter = paiementToPaiementDtoConverter;
        this.paiementRequestToPaiement = paiementRequestToPaiement;
    }

    @GetMapping
    public Result getAll()
    {
        List<Paiement> paiements = paiementService.findAll();
       return new Result(true,StatusCode.SUCCESS,"Tous les paiements",this.paiementToPaiementDtoConverter.convert(paiements));
    }

    @GetMapping("/{paiementId}")
    public Result getById(@PathVariable("paiementId") String paiementId){
        Paiement paiement = this.paiementService.findById(Long.parseLong(paiementId));
        return new Result(true,StatusCode.SUCCESS,"paiement retrouvé",this.paiementToPaiementDtoConverter.convert(paiement));
    }



    @DeleteMapping("/{paiementId}")
    public Result deleteMagasin(@PathVariable("paiementId") String paiementId){
        this.paiementService.deleteById(Long.parseLong(paiementId));
         return new Result(true,StatusCode.SUCCESS,"paiement annulé");
    }

    @PostMapping("modepaiement/{modepaiementId}/commande/{commandeId}")
    public Result save( @RequestBody PaiementDto paiementDto,@PathVariable("modepaiementId") String modePaiementId, @PathVariable("commandeId") String commandeId) {

       //Convert to paiement
        Paiement paiementTobeSave = this.paiementDtoToPaiementConverter.convert(paiementDto,modePaiementId,commandeId);
       // System.out.println(paiementTobeSave);*/
        //Update
        Paiement paiementSaved = this.paiementService.save(paiementTobeSave);
//
//        //Convert to dto
        PaiementDto paiementDtoresponse = this.paiementToPaiementDtoConverter.convert(paiementSaved);

        return new Result(true,StatusCode.SUCCESS,"Paiement crée",paiementDtoresponse);
    }

    @PatchMapping("/{paiementId}")
    public Result update( PaiementRequest paiementRequest){
        //request to paiment
        Paiement paiement = this.paiementRequestToPaiement.convert(paiementRequest);
        //update paiement
       Paiement updatedPaiement =  this.paiementService.update(paiement,Long.parseLong(paiementRequest.paiementId()));
       //Paiement to paiement dto
        PaiementDto paiementDto =this.paiementToPaiementDtoConverter.convert(updatedPaiement);

        return new Result(true,StatusCode.SUCCESS,"Paiement crée",paiementDto);
    }


    }
