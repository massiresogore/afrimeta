package com.msr.cg.afrimeta.taille;

import com.msr.cg.afrimeta.system.Result;
import com.msr.cg.afrimeta.system.StatusCode;
import com.msr.cg.afrimeta.taille.converter.TailleToTailleDtoConverter;
import com.msr.cg.afrimeta.taille.dto.TailleDto;
import com.msr.cg.afrimeta.taille.converter.TailleDtoToTailleConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Controller
@RequestMapping("${api.endpoint.base-url}/taille")
public class TailleController {
    private final TailleService tailleService;
    private final TailleDtoToTailleConverter tailleDtoToTaille;
    private final TailleToTailleDtoConverter tailleToTailleDto;


    public TailleController(TailleService tailleService, TailleDtoToTailleConverter tailleDtoToTaille, TailleToTailleDtoConverter tailleToTailleDto) {
        this.tailleService = tailleService;
        this.tailleDtoToTaille = tailleDtoToTaille;
        this.tailleToTailleDto = tailleToTailleDto;
    }


    @GetMapping
    public Result getAllTailles()
    {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "toutes les tailles",
                this.tailleService
                        .findAll().stream()
                        .map(taille -> new TailleDto(taille.getTailleId(),taille.getNom())).toList()
        );
    }

    @GetMapping("/{tailleId}")
    public Result getWebsiteById(@PathVariable("tailleId") String tailleId){

        return new Result(
                true,
                StatusCode.SUCCESS,
                "taille retrouvé",
                this.tailleToTailleDto.convert(this.tailleService.findById(Long.parseLong(tailleId))));
    }

    @PatchMapping("/{tailleId}")
    public Result updateTaille(@PathVariable("tailleId") String tailleId, @RequestBody TailleDto tailleDto){
        return new Result(
                true,
                StatusCode.SUCCESS,
                "taille mis a jour",
                this.tailleToTailleDto
                        .convert(this.tailleService
                                .update(this.tailleDtoToTaille
                                        .convert(tailleDto),Long.parseLong(tailleId))
                        )
        );
    }

    @DeleteMapping("/{tailleId}")
    public Result deleteTaille(@PathVariable("tailleId") String tailleId){
        this.tailleService.deleteById(Long.parseLong(tailleId));
        return new Result(true,StatusCode.SUCCESS,"taille supprimé");
    }

    @PostMapping
    public Result saveTailleId(@RequestBody TailleDto tailleDto){
        return new Result(
                true,
                StatusCode.SUCCESS,
                "taille crée",
                this.tailleToTailleDto
                        .convert(this.tailleService
                                .save(this.tailleDtoToTaille.convert(tailleDto)))
        );
    }
}
