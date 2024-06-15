package com.msr.cg.afrimeta.modelivraison;

import com.msr.cg.afrimeta.modelivraison.converter.ModeLivraisonDtoToModeLivraisonConverter;
import com.msr.cg.afrimeta.modelivraison.converter.ModeLivraisonToModeLivraisonDtoConverter;
import com.msr.cg.afrimeta.modelivraison.dto.ModeLivraisonDto;
import com.msr.cg.afrimeta.system.Result;
import com.msr.cg.afrimeta.system.StatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("${api.endpoint.base-url}/modelivraisons")
public class ModeLivraisonController {
    private final ModeLivraisonService modeLivraisonService;
    private final ModeLivraisonDtoToModeLivraisonConverter modeLivraisonDtoToModeLivraison;
    private final ModeLivraisonToModeLivraisonDtoConverter modeLivraisonToModeLivraisonDto;

    public ModeLivraisonController(ModeLivraisonService modeLivraisonService, ModeLivraisonDtoToModeLivraisonConverter modeLivraisonDtoToModeLivraison, ModeLivraisonToModeLivraisonDtoConverter modeLivraisonToModeLivraisonDto) {
        this.modeLivraisonService = modeLivraisonService;
        this.modeLivraisonDtoToModeLivraison = modeLivraisonDtoToModeLivraison;
        this.modeLivraisonToModeLivraisonDto = modeLivraisonToModeLivraisonDto;
    }

    @GetMapping
    public Result getAllModeLivraisons()
    {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "toutes les modelivraisons",
                this.modeLivraisonService
                        .findAll().stream()
                        .map(modeLivraison -> new ModeLivraisonDto(modeLivraison.getModeLivraisonId(),modeLivraison.getNom())).toList()
        );
    }

    @GetMapping("/{modeLivraisonId}")
    public Result geModeLivraisonById(@PathVariable("modeLivraisonId") String modeLivraisonId){

        return new Result(
                true,
                StatusCode.SUCCESS,
                "modeLivraison retrouvée",
                this.modeLivraisonToModeLivraisonDto.convert(this.modeLivraisonService.findById(Long.parseLong(modeLivraisonId))));
    }

    @PatchMapping("/{modeLivraisonId}")
    public Result updateModeLivraison(@PathVariable("modeLivraisonId") String modeLivraisonId, @RequestBody ModeLivraisonDto modeLivraisonDto){
        return new Result(
                true,
                StatusCode.SUCCESS,
                "modeLivraison mis a jour",
                this.modeLivraisonToModeLivraisonDto
                        .convert(this.modeLivraisonService
                                .update(this.modeLivraisonDtoToModeLivraison
                                        .convert(modeLivraisonDto),Long.parseLong(modeLivraisonId))
                        )
        );
    }

    @DeleteMapping("/{modeLivraisonId}")
    public Result deleteModeLivraison(@PathVariable("modeLivraisonId") String modeLivraisonId){
        this.modeLivraisonService.deleteById(Long.parseLong(modeLivraisonId));
        return new Result(true,StatusCode.SUCCESS,"modeLivraison supprimée");
    }

    @PostMapping
    public Result saveModeLivraison(@RequestBody ModeLivraisonDto modeLivraisonDto){
        return new Result(
                true,
                StatusCode.SUCCESS,
                "modeLivraison crée",
                this.modeLivraisonToModeLivraisonDto
                        .convert(this.modeLivraisonService
                                .save(this.modeLivraisonDtoToModeLivraison.convert(modeLivraisonDto)))
        );
    }
}
