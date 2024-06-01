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

        if(produitRequest.image().getSize()>0){
           this.storageService.storeProduitAndImage(produitRequest);
        }else {
            this.produitService.save(produitRequest);
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

  /*  @PatchMapping("/{produitId}")
    public Result update(@PathVariable("produitId") String produitId,@Valid @RequestBody ProduitDto produitDto) {
      Produit produitUpdated =  this.produitService.update(this.produitDtoToProduitConverter.convert(produitDto), Long.valueOf(produitId));
        System.out.println(produitUpdated);
        return new Result(
                true,
                200,
                "produit mis a jour"//,
                //this.produitDtoSercice.converterToProduitDto(this.produitService.update(this.produitDtoToProduitConverter.convert(produitDto), Long.valueOf(produitId)))
        );
    }*/



   /*@GetMapping("/{produitId}")
    public Result show(@PathVariable("produitId") String produitId) {
        System.out.println(this.produitService.singleProduitByProduitId(produitId));
        return new Result(
                true,
                200,
                "produit retrouvé",
                this.produitDtoSercice.converterToProduitDtoList(this.produitService.findById(Long.valueOf(produitId)))
        );
    }*/
    /*

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

    *//******************************************** les images par produits **************************//*
    @GetMapping("/images/{produitId}")
    public Result getImageByProduitId(@PathVariable("produitId") String produitId) throws IOException {
        return new Result(true,200,"toutes les images de ce produit",this.imageService.downloadImagesFromFileSystem(Long.valueOf(produitId)));
    }

    *//******************************************** Ajouter une image dans un produit **************************//*
    @PostMapping("/images/{produitId}")
    public Result addImage(@PathVariable("produitId") String produitId, @RequestParam("image") MultipartFile image) throws IOException {
        return new Result(true,200,this.imageService.uploadImageToFileSystem(image,produitId));
    }

    *//******************************************** une image par nom de l'image **************************//*
    @GetMapping("/image/{imageName}")
    public Result getImageByImageName(@PathVariable("produitId") String imageName) throws IOException {
        return new Result(true,200,"toutes les images de ce produit",this.imageService.downloadImageFromFileSystem(imageName));
    }
*/

  /*  @PostMapping("/images/{produitId}")
    public Result addImage(@PathVariable("produitId") String produitId, @RequestParam("image") MultipartFile image) throws IOException {
        return new Result(true,200,this.produitService.uploadImageToFileSystem(image,produitId));
    }*/
}
