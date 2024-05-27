package com.msr.cg.afrimeta.produit.dto.converter;

import com.msr.cg.afrimeta.image.ImageService;
import com.msr.cg.afrimeta.produit.Produit;
import com.msr.cg.afrimeta.produit.dto.dto.ProduitDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ProduitToProduitDtoConverter implements Converter<Produit, ProduitDto> {
    private final ImageService imageService;

    public ProduitToProduitDtoConverter(ImageService imageService) {
        this.imageService = imageService;
    }

    @Override
    public ProduitDto convert(Produit source) {
       List<String> imageNames = source.getImages().stream().map(image -> image.getFilePath()).toList();
//       List<String> imageNames = source.getImages().stream().map(image -> image.getName()).toList();

     /*  List<List<byte[]>> images = new  ArrayList<>();

        //pour plusieur images
       for(String name : imageNames ){
           try {
                images.add(this.imageService.downloadImageFromFileSystem(name));
           } catch (IOException e) {
               throw new RuntimeException(e);
           }
       }*/

        //pour une image
      /* List<byte[]> imageByte =


               imageName.stream().map(name -> {
            try {
                return this.imageService.downloadImageFromFileSystem(name);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
*/
//        return null;


        return new ProduitDto(
                source.getProduitId(),
                source.getTitre(),
                source.getDescription(),
                source.getQuantiteStock(),
                source.getPrix(),
                source.getDateAjout(),
                source.getCategorie(),
                source.getTypeProduit(),
                        null,
                source.getCouleurs(),
null,imageNames
//                images
        );


    }

    public List<ProduitDto> convert(List<Produit> source) {
        List<ProduitDto> produitDtos = new ArrayList<>();
        for (Produit produit : source) {

            produitDtos.add(this.convert(produit));
        }
        return produitDtos;
    }
}
