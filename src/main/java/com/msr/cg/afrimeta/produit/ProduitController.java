package com.msr.cg.afrimeta.produit;

import com.msr.cg.afrimeta.image.ImageService;
import com.msr.cg.afrimeta.produit.dto.dto.ProduitDto;
import com.msr.cg.afrimeta.produit.dto.converter.ProduitDtoToProduitConverter;
import com.msr.cg.afrimeta.produit.dto.converter.ProduitToProduitDtoConverter;
import com.msr.cg.afrimeta.produit.dto.dto.ProduitResponse;
import com.msr.cg.afrimeta.system.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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


   /* @GetMapping("website/{websiteId}")
    public Result getAllProduitByWebsiteId(@PathVariable String websiteId) {
        return new Result(
                true,
                200,
                "tous les produits de website",
                this.produitToProduitDtoConverter.convert(this.produitService.selectProduitWithCategorieAndTypeProduitAndImagesByWebsiteId(websiteId))
        );
    }*/

    //On ajoute un produit appatenant a un website
    // a faire , ajout image avvec id de website
    @PostMapping
    public Result store(@RequestBody ProduitDto produitDto) {
        return new Result(
                true,
                200,
                "produit crée",
               this.produitToProduitDtoConverter.convert( this.produitService.save(this.produitDtoToProduitConverter.convert(produitDto)))
//                this.produitToProduitDtoConverter.converterToProduitDto(this.produitService.save(this.produitDtoToProduitConverter.convert(produitDto),produitDto.couleur()))
        );
    }


   /* @GetMapping("bataclan")
    public Result findAllPageable(Pageable pageable) {
//        List<Produit>  produits =  this.produitService.findAllPageable(pageable).getContent();
//        List<ProduitDto> produitDtos =this.produitToProduitDtoConverter.convert(produits);
//        List<Object> data = new ArrayList<>();
//        Map<String,Object> map = new HashMap<>();
//        map.put("produits",produitDtos);
//        map.put("pageable",pageable);
//        map.put("sort",pageable.getSort());
//        map.put("numberOfElements",pageable.toLimit());


        return new Result(
                true,
                200,
                "tous les produits de website",
              this.produitService.findAllPageable(pageable)

        );
    }*/

     @GetMapping("bataclan")
    public Result findAllPageable(Pageable pageable) {
         Page<ProduitResponse> produitDtos = this.produitService.findAllPageable(pageable).map(this.produitToProduitDtoConverter::convert);

        return new Result(
                true,
                200,
                "tous les produits de website",
                produitDtos
        );
    }

/*
 @GetMapping("bataclan")
    public ResultPagination findAllPageable(Pageable pageable) {
        List<Produit>  produits =  this.produitService.findAllPageable(pageable).getContent();
        List<ProduitDto> produitDtos =this.produitToProduitDtoConverter.convert(produits);
        Map<String,Object> map = new HashMap<>();
        map.put("produits",produitDtos);
        map.put("pageable",pageable);
        map.put("sort",pageable.getSort());
        map.put("numberOfElements",pageable.toLimit());

        return new ResultPagination(
                true,
                200,
                "tous les produits de website",
                    map
        );
    }

*/


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

  /*  @PostMapping("/images/{produitId}")
    public Result addImage(@PathVariable("produitId") String produitId, @RequestParam("image") MultipartFile image) throws IOException {
        return new Result(true,200,this.produitService.uploadImageToFileSystem(image,produitId));
    }*/
}
