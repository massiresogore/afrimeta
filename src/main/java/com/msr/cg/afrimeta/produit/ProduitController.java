package com.msr.cg.afrimeta.produit;

import com.msr.cg.afrimeta.produit.dto.ProduitDtoToProduitConverter;
import com.msr.cg.afrimeta.produit.dto.ProduitDtoSercice;
import com.msr.cg.afrimeta.produit.dto.ProduitDto;
import com.msr.cg.afrimeta.system.Result;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Controller
@RequestMapping("${api.endpoint.base-url}/produits")
public class ProduitController{
    private final ProduitService produitService;
    private final ProduitDtoToProduitConverter produitDtoToProduitConverter;
    private final ProduitDtoSercice produitDtoSercice;

    public ProduitController(ProduitService produitService, ProduitDtoToProduitConverter produitDtoToProduitConverter, ProduitDtoSercice produitDtoSercice) {
        this.produitService = produitService;
        this.produitDtoToProduitConverter = produitDtoToProduitConverter;
        this.produitDtoSercice = produitDtoSercice;
    }

    @GetMapping("website/{websiteId}")
    public Result getProduitByWebsiteId(@PathVariable String websiteId) {
        return new Result(
                true,
                200,
                "tous les produits de website avec leur catégorie et type",
                this.produitDtoSercice.converterToProduitDtoList(this.produitService.selectProduitByWebsiteId(websiteId))
        );
    }

    @GetMapping("/{produitId}")
    public Result show(@PathVariable("produitId") String produitId) {
        return new Result(
                true,
                200,
                "produit retrouvé",
                this.produitDtoSercice.converterToProduitDto(this.produitService.singleProduitByProduitId(produitId))
        );
    }
    @PostMapping
    public Result store(@RequestBody ProduitDto produitDto) {
        return new Result(
                true,
                200,
                "produit crée",
                this.produitDtoSercice.converterToProduitDto(this.produitService.save(this.produitDtoToProduitConverter.convert(produitDto),produitDto.couleur()))
        );
    }

    @PatchMapping("/{produitId}")
    public Result update(@PathVariable("produitId") String produitId,@Valid @RequestBody ProduitDto produitDto) {
        return new Result(
                true,
                200,
                "produit mis a jour",
                this.produitDtoSercice.converterToProduitDto(this.produitService.update(this.produitDtoToProduitConverter.convert(produitDto), Long.valueOf(produitId)))
        );
    }

    @DeleteMapping("/{produitId}")
    public Result delete(@PathVariable("produitId") String produitId) {
        this.produitService.deleteById(Long.valueOf(produitId));
        return new Result(true, 200, "produit supprimé");
    }
}
