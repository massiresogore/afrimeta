package com.msr.cg.afrimeta.typeproduit;

import com.msr.cg.afrimeta.system.Result;
import com.msr.cg.afrimeta.system.StatusCode;
import com.msr.cg.afrimeta.typeproduit.converter.TypeProduitDtoToTypeProduitConverter;
import com.msr.cg.afrimeta.typeproduit.converter.TypeProduitToTypeProduitDtoConverter;
import com.msr.cg.afrimeta.typeproduit.dto.TypeProduitDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Controller
@RequestMapping("${api.endpoint.base-url}/type-produit")
public class TypeProduitController {
    private final TypeProduitService typeProduitService;
    private final TypeProduitDtoToTypeProduitConverter typeProduitDtoToTypeProduit;
    private final TypeProduitToTypeProduitDtoConverter typeProduitToTypeProduitDto;


    public TypeProduitController(TypeProduitService typeProduitService, TypeProduitDtoToTypeProduitConverter typeProduitDtoToTypeProduit, TypeProduitToTypeProduitDtoConverter typeProduitToTypeProduitDto) {
        this.typeProduitService = typeProduitService;
        this.typeProduitDtoToTypeProduit = typeProduitDtoToTypeProduit;
        this.typeProduitToTypeProduitDto = typeProduitToTypeProduitDto;
    }


    @GetMapping
    public Result getAllTypeProduits()
    {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "tous les type de produits",
                this.typeProduitService
                        .findAll().stream()
                        .map(typeProduit -> new TypeProduitDto(typeProduit.getTypeProduitId(),typeProduit.getNom())).toList()
        );
    }

    @GetMapping("/{typeProduitId}")
    public Result getWebsiteById(@PathVariable("typeProduitId") String typeProduitId){

        return new Result(
                true,
                StatusCode.SUCCESS,
                "type de produit retrouvé",
                this.typeProduitToTypeProduitDto.convert(this.typeProduitService.findById(Long.parseLong(typeProduitId))));
    }

    @PatchMapping("/{typeProduitId}")
    public Result updateTypeProduit(@PathVariable("typeProduitId") String typeProduitId, @RequestBody TypeProduitDto typeProduitDto){
        return new Result(
                true,
                StatusCode.SUCCESS,
                "type de produit mis a jour",
                this.typeProduitToTypeProduitDto
                        .convert(this.typeProduitService
                                .update(this.typeProduitDtoToTypeProduit
                                        .convert(typeProduitDto),Long.parseLong(typeProduitId))
                        )
        );
    }

    @DeleteMapping("/{typeProduitId}")
    public Result deleteTypeProduit(@PathVariable("typeProduitId") String typeProduitId){
        this.typeProduitService.deleteById(Long.parseLong(typeProduitId));
        return new Result(true,StatusCode.SUCCESS,"type produit supprimé");
    }

    @PostMapping
    public Result saveTypeProduitId(@RequestBody TypeProduitDto typeProduitDto){
        return new Result(
                true,
                StatusCode.SUCCESS,
                "type de produit crée",
                this.typeProduitToTypeProduitDto
                        .convert(this.typeProduitService
                                .save(this.typeProduitDtoToTypeProduit.convert(typeProduitDto)))
        );
    }
}
