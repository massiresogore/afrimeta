package com.msr.cg.afrimeta.produit;

import com.msr.cg.afrimeta.produit.dto.dto.ProduitDto;
import com.msr.cg.afrimeta.produit.dto.converter.ProduitDtoToProduitConverter;
import com.msr.cg.afrimeta.produit.dto.converter.ProduitToProduitDtoConverter;
import com.msr.cg.afrimeta.produit.dto.dto.ProduitRequest;
import com.msr.cg.afrimeta.produit.dto.dto.ProduitResponse;
import com.msr.cg.afrimeta.storage.StorageService;
import com.msr.cg.afrimeta.system.Result;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@CrossOrigin
@RestController
@Controller
@RequestMapping("${api.endpoint.base-url}/produits")
public class ProduitController{
    private final ProduitService produitService;
    private final ProduitDtoToProduitConverter produitDtoToProduitConverter;
    private final ProduitToProduitDtoConverter produitToProduitDtoConverter;
    private final StorageService storageService;

    public ProduitController(ProduitService produitService, ProduitDtoToProduitConverter produitDtoToProduitConverter, ProduitToProduitDtoConverter produitToProduitDtoConverter, StorageService storageService) {
        this.produitService = produitService;
        this.produitDtoToProduitConverter = produitDtoToProduitConverter;
        this.produitToProduitDtoConverter = produitToProduitDtoConverter;
        this.storageService = storageService;
    }


    @GetMapping("website/{websiteId}")
    public Result getAllProduitByWebsiteId(@PathVariable String websiteId) {
        return new Result(
                true,
                200,
                "tous les produits de website N°"+websiteId+"!",
                this.produitToProduitDtoConverter.convert(this.produitService.selectProduitWithCategorieAndTypeProduitAndImagesByWebsiteId(websiteId))
        );
    }

    //On ajoute un produit appatenant a un website
    // a faire , ajout image avvec id de website
    @PostMapping("/{websiteId}")
    public Result store(ProduitRequest produitRequest) {

        Produit produit = produitDtoToProduitConverter.convert(produitRequest);
        if(produitRequest.image().getSize()>0){
           this.storageService.storeProduitAndImage(produitRequest);
        }else {
            this.produitService.save(produit);
        }
        return new Result(
                true,
                200,
                "produit crée"
        );
    }


     @GetMapping("bataclan")
    public Result findAllPageable(Pageable pageable) {
         Page<ProduitResponse> produitDtos = this.produitService.findAllPageable(pageable).map(this.produitToProduitDtoConverter::convert);
        return new Result(
                true,
                200,
                "tous les produits de website avec leur catégorie et type",
                produitDtos
        );
    }
    @DeleteMapping("/{produitId}")
    public Result delete(@PathVariable("produitId") String produitId) {
        this.produitService.deleteById(Long.valueOf(produitId));
        return new Result(true, 200, "produit supprimé");
    }

//    @GetMapping("/{produitId}")
//    public String show(@PathVariable("produitId") String produitId, Model model) {
//        model.addAttribute("produit",this.produitToProduitDtoConverter.convert(this.produitService.singleProduitByProduitId(produitId)));
//
//        return "produits";
//
//
//    }
//
   @GetMapping("/{produitId}")
    public Result show(@PathVariable("produitId") String produitId) {
        return new Result(
                true,
                200,
                "produit retrouvé",
                this.produitToProduitDtoConverter.convert(this.produitService.singleProduitByProduitId(produitId))
        );
    }



}
