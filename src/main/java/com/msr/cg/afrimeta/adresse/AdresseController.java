package com.msr.cg.afrimeta.adresse;

import com.msr.cg.afrimeta.adresse.converter.AdresseDtoToAdresseConcverter;
import com.msr.cg.afrimeta.adresse.converter.AdresseToAdresseDtoConverter;
import com.msr.cg.afrimeta.adresse.dto.AdresseDto;
import com.msr.cg.afrimeta.system.Result;
import com.msr.cg.afrimeta.system.StatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("${api.endpoint.base-url}/adresses")
public class AdresseController {

    private final AdresseService adresseService;
    private final AdresseDtoToAdresseConcverter adresseDtoToAdresseConcverter;
    private final AdresseToAdresseDtoConverter adresseToAdresseDtoConverter;

    public AdresseController(AdresseService adresseService, AdresseDtoToAdresseConcverter adresseDtoToAdresseConcverter, AdresseToAdresseDtoConverter adresseToAdresseDtoConverter) {
        this.adresseService = adresseService;
        this.adresseDtoToAdresseConcverter = adresseDtoToAdresseConcverter;
        this.adresseToAdresseDtoConverter = adresseToAdresseDtoConverter;
    }

    @PostMapping
    public Result store(@RequestBody AdresseDto adresseDto) {

        return new Result(true, StatusCode.SUCCESS,"adresse ajoutée",
                this.adresseToAdresseDtoConverter
                        .convert(this.adresseService.save(adresseDtoToAdresseConcverter.convert(adresseDto))));
    }

    @GetMapping
    public Result getAdresses() {
        return new Result(
                true,
                StatusCode.OK,
                "toutes les adresses",
                adresseService.findAll().stream().map(adresseToAdresseDtoConverter::convert).toList()
        );
    }

    @GetMapping("/{adresseId}")
    public Result findById(@PathVariable("adresseId") String adresseId) {
        return new Result(true, StatusCode.SUCCESS,"adresse retrouvée",  this.adresseToAdresseDtoConverter
                .convert(this.adresseService.findById(Long.parseLong(adresseId))));
    }

    @PatchMapping("/{adresseId}")
    public Result updateAdresse(@PathVariable("adresseId") String adresseId, @RequestBody AdresseDto adresseDto) {
        return new Result(true, StatusCode.SUCCESS,"adresse mis à jour",
                adresseToAdresseDtoConverter.convert(this.adresseService
                        .update(this.adresseDtoToAdresseConcverter.convert(adresseDto),Long.parseLong(adresseId)))
        );
    }

    @DeleteMapping("/{adresseId}")
    public Result deleteAdresse(@PathVariable("adresseId") String adresseId) {

        this.adresseService.deleteById(Long.parseLong(adresseId));
        return new Result(true, StatusCode.SUCCESS, "adresse supprimée");
    }

}
