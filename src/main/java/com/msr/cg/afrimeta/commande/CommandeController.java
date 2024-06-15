package com.msr.cg.afrimeta.commande;

import com.msr.cg.afrimeta.commande.converter.CommandeRequestToCommandeConverter;
import com.msr.cg.afrimeta.commande.converter.CommandeToCommandeDtoConverter;
import com.msr.cg.afrimeta.commande.dto.CommandeDto;
import com.msr.cg.afrimeta.commande.request.CommandeRequest;
import com.msr.cg.afrimeta.system.Result;
import com.msr.cg.afrimeta.system.StatusCode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.endpoint.base-url}/commandes")
public class CommandeController {
    private final CommandeService commandeService;
    private final CommandeRequestToCommandeConverter commandeRequestToCommandeConverter;
    private final CommandeToCommandeDtoConverter commandeToCommandeDtoConverter;


    public CommandeController(CommandeService commandeService, CommandeRequestToCommandeConverter commandeRequestToCommandeConverter, CommandeToCommandeDtoConverter commandeToCommandeDtoConverter) {
        this.commandeService = commandeService;
        this.commandeRequestToCommandeConverter = commandeRequestToCommandeConverter;
        this.commandeToCommandeDtoConverter = commandeToCommandeDtoConverter;
    }

    @GetMapping
    public Result getCommandes() {

        return new Result(true, StatusCode.SUCCESS,"Toutes les commandes",this.commandeToCommandeDtoConverter.convert(this.commandeService.findAll()));
    }

    @PostMapping("/client/{clientUserId}")
    public Result save(@Valid @RequestBody CommandeRequest commandeRequest, @NotNull @PathVariable("clientUserId") String clientUserId) {
        //Convert to commande
        Commande newComande = commandeRequestToCommandeConverter.convert(commandeRequest, clientUserId);

        //Save Commande with produit
        Commande savedCommande = this.commandeService.save(newComande,commandeRequest.produitIds());

       // System.out.println(newComande);
        /*for (String id: commandeRequest.produitIds()){
            System.out.println(id);
        }*/
        return new Result(true,StatusCode.SUCCESS,"Commande enregistré avec success");
    }

    /*@PostMapping("/client/{clientUserId}")
    public Result save(CommandeRequest commandeRequest) {

        //Convert to commande
        Commande newComande = commandeRequestToCommandeConverter.convert(commandeRequest);

        //Save
        Commande savedCommande = this.commandeService.save(newComande);

        //Convert to dto
        CommandeDto commandeDto = this.commandeToCommandeDtoConverter.convert(savedCommande);

        return new Result(true,StatusCode.SUCCESS,"Commande enregistré avec success",commandeDto);
    }*/

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

}
