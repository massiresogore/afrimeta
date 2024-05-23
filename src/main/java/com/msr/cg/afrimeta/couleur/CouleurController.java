package com.msr.cg.afrimeta.couleur;

import com.msr.cg.afrimeta.couleur.converter.CouleurDtoToCouleurConverter;
import com.msr.cg.afrimeta.couleur.converter.CouleurToCouleurDtoConverter;
import com.msr.cg.afrimeta.couleur.dto.CouleurDto;
import com.msr.cg.afrimeta.system.Result;
import com.msr.cg.afrimeta.system.StatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("${api.endpoint.base-url}/couleurs")
public class CouleurController {
    private final CouleurService couleurService;
    private final CouleurDtoToCouleurConverter couleurDtoToCouleur;
    private final CouleurToCouleurDtoConverter couleurToCouleurDto;

    public CouleurController(CouleurService couleurService, CouleurDtoToCouleurConverter couleurDtoToCouleur, CouleurToCouleurDtoConverter couleurToCouleurDto) {
        this.couleurService = couleurService;
        this.couleurDtoToCouleur = couleurDtoToCouleur;
        this.couleurToCouleurDto = couleurToCouleurDto;
    }

    @GetMapping
    public Result getAllCouleurs()
    {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "toutes les couleurs",
                this.couleurService
                        .findAll().stream()
                        .map(couleur -> new CouleurDto(couleur.getCouleurId(),couleur.getNom())).toList()
        );
    }

    @GetMapping("/{couleurId}")
    public Result geCouleurById(@PathVariable("couleurId") String couleurId){

        return new Result(
                true,
                StatusCode.SUCCESS,
                "couleur retrouvée",
                this.couleurToCouleurDto.convert(this.couleurService.findById(Long.parseLong(couleurId))));
    }

    @PatchMapping("/{couleurId}")
    public Result updateCouleur(@PathVariable("couleurId") String couleurId, @RequestBody CouleurDto couleurDto){
        return new Result(
                true,
                StatusCode.SUCCESS,
                "couleur mis a jour",
                this.couleurToCouleurDto
                        .convert(this.couleurService
                                .update(this.couleurDtoToCouleur
                                        .convert(couleurDto),Long.parseLong(couleurId))
                        )
        );
    }

    @DeleteMapping("/{couleurId}")
    public Result deleteCouleur(@PathVariable("couleurId") String couleurId){
        this.couleurService.deleteById(Long.parseLong(couleurId));
        return new Result(true,StatusCode.SUCCESS,"couleur supprimée");
    }

    @PostMapping
    public Result saveCouleur(@RequestBody CouleurDto couleurDto){
        return new Result(
                true,
                StatusCode.SUCCESS,
                "couleur crée",
                this.couleurToCouleurDto
                        .convert(this.couleurService
                                .save(this.couleurDtoToCouleur.convert(couleurDto)))
        );
    }
}
