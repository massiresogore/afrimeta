package com.msr.cg.afrimeta.ville;

import com.msr.cg.afrimeta.system.Result;
import com.msr.cg.afrimeta.system.StatusCode;
import com.msr.cg.afrimeta.ville.converter.VilleDtoToVilleConverter;
import com.msr.cg.afrimeta.ville.converter.VilleToVilleDtoConverter;
import com.msr.cg.afrimeta.ville.dto.VilleDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RestController
@RequestMapping("${api.endpoint.base-url}/villes")
public class VilleController {

    private final VilleService villeService;
    private final VilleDtoToVilleConverter villeDtoToVilleConverter;
    private final VilleToVilleDtoConverter villeToVilleDtoConverter;

    public VilleController(VilleService villeService,
                           VilleDtoToVilleConverter villeDtoToVilleConverter,
                           VilleToVilleDtoConverter villeToVilleDtoConverter) {
        this.villeService = villeService;
        this.villeDtoToVilleConverter = villeDtoToVilleConverter;
        this.villeToVilleDtoConverter = villeToVilleDtoConverter;
    }

    @PostMapping
    public Result store(@RequestBody VilleDto villeDto) {

        return new Result(true, StatusCode.SUCCESS,"ville ajoutée",
                this.villeToVilleDtoConverter
                        .convert(this.villeService.save(villeDtoToVilleConverter.convert(villeDto))));
    }

    @GetMapping
    public Result getVilles() {
        return new Result(
                true,
                StatusCode.OK,
                "toutes les villes",
                villeService.findAll().stream().map(villeToVilleDtoConverter::convert).toList()
        );
    }

    @GetMapping("/{villeId}")
    public Result findById(@PathVariable("villeId") String id) {
        //find
        //convert
        //send find
        return new Result(true, StatusCode.SUCCESS,"ville retrouvée",  this.villeToVilleDtoConverter
                .convert(this.villeService.findById(Integer.parseInt(id))));
    }

    @PutMapping("/{villeId}")
    public Result updateVille(@PathVariable("villeId") String villeId, @RequestBody VilleDto villeDto) {
        // convert villeDto to Ville
        // update
        // reconvert to VilleDto
        // return
        return new Result(true, StatusCode.SUCCESS,"ville mis à jour",
                villeToVilleDtoConverter.convert(this.villeService
                        .update(this.villeDtoToVilleConverter.convert(villeDto),Long.parseLong(villeId)))
        );
    }

    @DeleteMapping("/{villeId}")
    public Result deleteVille(@PathVariable("villeId") String villeId) {

        this.villeService.deleteById(Long.parseLong(villeId));
        return new Result(true, StatusCode.SUCCESS, "ville supprimée");
    }


}
