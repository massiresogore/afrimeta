package com.msr.cg.afrimeta.commande;

import com.msr.cg.afrimeta.clientUser.ClientUserService;
import com.msr.cg.afrimeta.commande.converter.CommandeRequestToCommandeConverter;
import com.msr.cg.afrimeta.commande.converter.CommandeToCommandeDtoConverter;
import com.msr.cg.afrimeta.commande.dto.CommandeDto;
import com.msr.cg.afrimeta.commande.request.CommandeRequest;
import com.msr.cg.afrimeta.commande.request.CommandeResponse;
import com.msr.cg.afrimeta.embaddable.Contenir;
import com.msr.cg.afrimeta.produit.Produit;
import com.msr.cg.afrimeta.produit.ProduitService;
import com.msr.cg.afrimeta.produit.dto.converter.ProduitDtoToProduitConverter;
import com.msr.cg.afrimeta.produit.dto.converter.ProduitToProduitDtoConverter;
import com.msr.cg.afrimeta.produit.dto.dto.ProduitResponse;
import com.msr.cg.afrimeta.system.Result;
import com.msr.cg.afrimeta.system.StatusCode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("${api.endpoint.base-url}/commandes")
public class CommandeController {
    private final CommandeService commandeService;
    private final CommandeRequestToCommandeConverter commandeRequestToCommandeConverter;
    private final CommandeToCommandeDtoConverter commandeToCommandeDtoConverter;
    private final ClientUserService clientUserService;
    private final ProduitService produitService;
    private final ProduitToProduitDtoConverter produitToProduitDtoConverter;


    public CommandeController(CommandeService commandeService, CommandeRequestToCommandeConverter commandeRequestToCommandeConverter, CommandeToCommandeDtoConverter commandeToCommandeDtoConverter, ClientUserService clientUserService, ProduitService produitService, ProduitToProduitDtoConverter produitToProduitDtoConverter) {
        this.commandeService = commandeService;
        this.commandeRequestToCommandeConverter = commandeRequestToCommandeConverter;
        this.commandeToCommandeDtoConverter = commandeToCommandeDtoConverter;
        this.clientUserService = clientUserService;
        this.produitService = produitService;
        this.produitToProduitDtoConverter = produitToProduitDtoConverter;
    }

    @GetMapping
    public Result getCommandes() {

        return new Result(true, StatusCode.SUCCESS,"Toutes les commandes",this.commandeToCommandeDtoConverter.convert(this.commandeService.findAll()));
    }

    @PostMapping("/client/{clientUserId}")
    public Result save(@Valid @RequestBody CommandeRequest commandeRequest, @NotNull @PathVariable("clientUserId") String clientUserId) {
        //Convert to commande
       // System.out.println(commandeRequest);
        Commande newComande = commandeRequestToCommandeConverter.convert(commandeRequest, clientUserId);
       // System.out.println(newComande);
        //Save Commande with produit
        Commande savedCommande = this.commandeService.save(newComande,commandeRequest.paniers());

        return new Result(true,StatusCode.SUCCESS,"Commande enregistré avec success");
    }

    @PatchMapping("/{updateCommandeId}/client/{clientUserId}")
    public Result update(CommandeRequest commandeRequest, @PathVariable String updateCommandeId) {
        //Convert to commande
        Commande commandeTobeUpdate = commandeRequestToCommandeConverter.convert(commandeRequest);

        //Update
        Commande updateCommande = this.commandeService.update(commandeTobeUpdate, Long.valueOf(updateCommandeId));

        //Convert to dto
        CommandeDto commandeDto = this.commandeToCommandeDtoConverter.convert(updateCommande);

        return new Result(true,StatusCode.SUCCESS,"Commande mis à jour !",commandeDto);
    }

    @DeleteMapping("/{commandeId}")
    public Result delete(@PathVariable("commandeId") String commandeId) {
        this.commandeService.deleteById(Long.valueOf(commandeId));
        return new Result(true,StatusCode.SUCCESS, "commande supprimée");
    }

    @GetMapping("/{commandeId}")
    public Result show(@PathVariable("commandeId") String commandeId) {
        return new Result(true,StatusCode.SUCCESS,"commande trouvée", this.commandeToCommandeDtoConverter.convert(this.commandeService.findById(Long.valueOf(commandeId))));
    }



    @GetMapping("client/{clientId}")
    public Result userCommandes(@PathVariable("clientId") String clientId) {
        CommandeResponse commandeResponses= this.commandeService.findByClientUser(clientId).stream().map(commande ->  {
            //Liste des enregistrement des commandes de ce client dans la table Contenir issue de la relation (commande_produi)
            List<Contenir> contenirs = commande.getContenirs();

            //Ses produits commandés
           List<Produit> produitCommandes= contenirs.stream().map(contenir -> {
                       Produit  p = this.produitService.findById(contenir.getProduit().getProduitId());
                       p.setQuantiteCommande(contenir.getQuantite());
                       return p;
                   }

           ).toList();

           //Convertir la liste des produits commandé en list des produitresponse
            List<ProduitResponse> produitResponses = this.produitToProduitDtoConverter.convert(produitCommandes);
            //System.out.println(produitResponses);

            return new CommandeResponse(
                    commande.getCommandeId(),
                    commande.getAdresse(),
                    produitResponses,
                    commande.getCreatedAt().toString(),
                    commande.getClientUser().getUsername(),
                    produitResponses.size(),
                    String.valueOf(commande.getCommandeTotal()),
                    commande.getCreatedAt().toString(),
                    commande.getUpdatedAt().toString()
            );
        }).toList().get(0);







        return new Result(true,StatusCode.SUCCESS,"commande trouvée",commandeResponses);
    }

}
