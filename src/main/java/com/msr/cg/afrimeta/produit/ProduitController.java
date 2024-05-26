package com.msr.cg.afrimeta.produit;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.msr.cg.afrimeta.image.ImageService;
import com.msr.cg.afrimeta.produit.dto.ProduitDtoSercice;
import com.msr.cg.afrimeta.produit.dto.ProduitDto;
import com.msr.cg.afrimeta.produit.dto.converter.ProduitDtoToProduitConverter;
import com.msr.cg.afrimeta.produit.dto.converter.ProduitToProduitDtoConverter;
import com.msr.cg.afrimeta.system.Result;
import com.msr.cg.afrimeta.website.Website;
import jakarta.validation.Valid;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@Controller
@RequestMapping("${api.endpoint.base-url}/produits")
public class ProduitController{
    private final ProduitService produitService;
    private final ImageService imageService;
    private final ProduitDtoToProduitConverter produitDtoToProduitConverter;
    private final ProduitToProduitDtoConverter produitToProduitDtoConverter;

    public ProduitController(ProduitService produitService, ImageService imageService, ProduitDtoToProduitConverter produitDtoToProduitConverter, ProduitToProduitDtoConverter produitToProduitDtoConverter) {
        this.produitService = produitService;
        this.imageService = imageService;
        this.produitDtoToProduitConverter = produitDtoToProduitConverter;
        this.produitToProduitDtoConverter = produitToProduitDtoConverter;
    }


    @GetMapping("website/{websiteId}")
    public Result getAllProduitByWebsiteId(@PathVariable String websiteId) {
        return new Result(
                true,
                200,
                "tous les produits de website",
                this.produitToProduitDtoConverter.convert(this.produitService.selectProduitWithCategorieAndTypeProduitAndImagesByWebsiteId(websiteId))
        );
    }

    private Website getWebsite(Produit produit) {
        System.out.println(produit);
       /* try {
            Website website=   produit.getWebsite();
            return website;
        } catch (org.hibernate.LazyInitializationException e) {
            // Gérer l'exception
            System.out.println("LazyInitializationException capturée : " + e.getMessage());
            return null;
        }*/
        return null;
        /*try {
            // Tentative d'accès à une collection en mode lazy
            List<Order> orders = customer.getOrders();
            orders.size(); // Forcer le chargement des données
        } catch (org.hibernate.LazyInitializationException e) {
            // Gérer l'exception
            System.out.println("LazyInitializationException capturée : " + e.getMessage());
        }

        */
       /* try {
          Website website=   produit.getWebsite();
          return website;
        }catch (HttpMessageConversionException exception){
            return null;
        } catch (Exception exception){
            return null;
        }*/
    }

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
    /* @PostMapping
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
}
