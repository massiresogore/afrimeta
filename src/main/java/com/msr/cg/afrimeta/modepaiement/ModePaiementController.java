package com.msr.cg.afrimeta.modepaiement;

import com.msr.cg.afrimeta.modepaiement.converter.ModePaiementDtoToModePaiementConverter;
import com.msr.cg.afrimeta.modepaiement.converter.ModePaiementToModePaiementDtoConverter;
import com.msr.cg.afrimeta.modepaiement.dto.ModePaiementDto;
import com.msr.cg.afrimeta.system.Result;
import com.msr.cg.afrimeta.system.StatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("${api.endpoint.base-url}/modepaiements")
public class ModePaiementController {
    private final ModePaiementService modePaiementService;
    private final ModePaiementDtoToModePaiementConverter modePaiementDtoToModePaiement;
    private final ModePaiementToModePaiementDtoConverter modePaiementToModePaiementDto;

    public ModePaiementController(ModePaiementService modePaiementService, ModePaiementDtoToModePaiementConverter modePaiementDtoToModePaiement, ModePaiementToModePaiementDtoConverter modePaiementToModePaiementDto) {
        this.modePaiementService = modePaiementService;
        this.modePaiementDtoToModePaiement = modePaiementDtoToModePaiement;
        this.modePaiementToModePaiementDto = modePaiementToModePaiementDto;
    }

    @GetMapping
    public Result getAllModePaiements()
    {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "toutes les modepaiements",
                this.modePaiementService
                        .findAll().stream()
                        .map(modePaiement -> new ModePaiementDto(modePaiement.getModePaiementId(),modePaiement.getNom())).toList()
        );
    }

    @GetMapping("/{modePaiementId}")
    public Result geModePaiementById(@PathVariable("modePaiementId") String modePaiementId){

        return new Result(
                true,
                StatusCode.SUCCESS,
                "modePaiement retrouvée",
                this.modePaiementToModePaiementDto.convert(this.modePaiementService.findById(Long.parseLong(modePaiementId))));
    }

    @PatchMapping("/{modePaiementId}")
    public Result updateModePaiement(@PathVariable("modePaiementId") String modePaiementId, @RequestBody ModePaiementDto modePaiementDto){
        return new Result(
                true,
                StatusCode.SUCCESS,
                "modePaiement mis a jour",
                this.modePaiementToModePaiementDto
                        .convert(this.modePaiementService
                                .update(this.modePaiementDtoToModePaiement
                                        .convert(modePaiementDto),Long.parseLong(modePaiementId))
                        )
        );
    }

    @DeleteMapping("/{modePaiementId}")
    public Result deleteModePaiement(@PathVariable("modePaiementId") String modePaiementId){
        this.modePaiementService.deleteById(Long.parseLong(modePaiementId));
        return new Result(true,StatusCode.SUCCESS,"modePaiement supprimée");
    }

    @PostMapping
    public Result saveModePaiement(@RequestBody ModePaiementDto modePaiementDto){
        return new Result(
                true,
                StatusCode.SUCCESS,
                "modePaiement crée",
                this.modePaiementToModePaiementDto
                        .convert(this.modePaiementService
                                .save(this.modePaiementDtoToModePaiement.convert(modePaiementDto)))
        );
    }
}
