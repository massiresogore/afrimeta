package com.msr.cg.afrimeta.categorie;

import com.msr.cg.afrimeta.categorie.converter.CategorieDtoToCategorieConverter;
import com.msr.cg.afrimeta.categorie.converter.CategorieToCategorieDtoConverter;

import com.msr.cg.afrimeta.categorie.dto.CategorieDto;
import com.msr.cg.afrimeta.system.Result;
import com.msr.cg.afrimeta.system.StatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("${api.endpoint.base-url}/categories")
public class CategorieController {
    private final CategorieService categorieService;
    private final CategorieDtoToCategorieConverter categorieDtoToCategorie;
    private final CategorieToCategorieDtoConverter categorieToCategorieDto;

    public CategorieController(CategorieService categorieService, CategorieDtoToCategorieConverter categorieDtoToCategorie, CategorieToCategorieDtoConverter categorieToCategorieDto) {
        this.categorieService = categorieService;
        this.categorieDtoToCategorie = categorieDtoToCategorie;
        this.categorieToCategorieDto = categorieToCategorieDto;
    }

    @GetMapping
    public Result getAllCategories()
    {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "toutes les categories",
                this.categorieService
                        .findAll().stream()
                        .map(categorie -> new CategorieDto(categorie.getCategorieId(),categorie.getNom())).toList()
        );
    }

    @GetMapping("/{categorieId}")
    public Result geCategorieById(@PathVariable("categorieId") String categorieId){

        return new Result(
                true,
                StatusCode.SUCCESS,
                "categorie retrouvée",
                this.categorieToCategorieDto.convert(this.categorieService.findById(Long.parseLong(categorieId))));
    }

    @PatchMapping("/{categorieId}")
    public Result updateCategorie(@PathVariable("categorieId") String categorieId, @RequestBody CategorieDto categorieDto){
        return new Result(
                true,
                StatusCode.SUCCESS,
                "categorie mis a jour",
                this.categorieToCategorieDto
                        .convert(this.categorieService
                                .update(this.categorieDtoToCategorie
                                        .convert(categorieDto),Long.parseLong(categorieId))
                        )
        );
    }

    @DeleteMapping("/{categorieId}")
    public Result deleteCategorie(@PathVariable("categorieId") String categorieId){
        this.categorieService.deleteById(Long.parseLong(categorieId));
        return new Result(true,StatusCode.SUCCESS,"categorie supprimée");
    }

    @PostMapping
    public Result saveCategorie(@RequestBody CategorieDto categorieDto){
        return new Result(
                true,
                StatusCode.SUCCESS,
                "categorie crée",
                this.categorieToCategorieDto
                        .convert(this.categorieService
                                .save(this.categorieDtoToCategorie.convert(categorieDto)))
        );
    }
}
