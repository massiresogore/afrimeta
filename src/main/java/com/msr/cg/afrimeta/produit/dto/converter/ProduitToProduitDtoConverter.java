package com.msr.cg.afrimeta.produit.dto.converter;

import com.msr.cg.afrimeta.categorie.converter.CategorieToCategorieDtoConverter;
import com.msr.cg.afrimeta.categorie.dto.CategorieResponse;
import com.msr.cg.afrimeta.couleur.dto.CouleurResponse;
import com.msr.cg.afrimeta.image.ImageDto;
import com.msr.cg.afrimeta.produit.Produit;
import com.msr.cg.afrimeta.produit.dto.dto.ProduitResponse;
import com.msr.cg.afrimeta.storage.FileSystemStorageService;
import com.msr.cg.afrimeta.storage.FileUploadController;
import com.msr.cg.afrimeta.typeproduit.converter.TypeProduitToTypeProduitDtoConverter;
import com.msr.cg.afrimeta.typeproduit.dto.TypeProduitResponse;
import com.msr.cg.afrimeta.website.converter.WebsiteToWebsiteDtoConverter;
import com.msr.cg.afrimeta.website.dto.SingleWebsiteResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.nio.file.Path;
import java.util.*;

@Component
public class ProduitToProduitDtoConverter implements Converter<Produit, ProduitResponse> {
    private final WebsiteToWebsiteDtoConverter websiteToWebsiteDtoConverter;
    private final CategorieToCategorieDtoConverter categorieToCategorieDtoConverter;
    private final TypeProduitToTypeProduitDtoConverter typeProduitToTypeProduitDtoConverter;
    private final FileSystemStorageService fileSystemStorageService;

    public ProduitToProduitDtoConverter(WebsiteToWebsiteDtoConverter websiteToWebsiteDtoConverter, CategorieToCategorieDtoConverter categorieToCategorieDtoConverter, TypeProduitToTypeProduitDtoConverter typeProduitToTypeProduitDtoConverter, FileSystemStorageService fileSystemStorageService) {
        this.websiteToWebsiteDtoConverter = websiteToWebsiteDtoConverter;
        this.categorieToCategorieDtoConverter = categorieToCategorieDtoConverter;
        this.typeProduitToTypeProduitDtoConverter = typeProduitToTypeProduitDtoConverter;
        this.fileSystemStorageService = fileSystemStorageService;
    }

    @Override
    public ProduitResponse convert(Produit source) {

        List<ImageDto> images = source.getImages().stream().map(
                image -> {
                    if(image.getName()==null){
                        return null;
                    }
                    Path retrievedPath = this.fileSystemStorageService.load(image.getName());
                    return    new ImageDto(image.getImageId(),MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                               "serveFile",retrievedPath.getFileName().toString()).build().toUri().toString());
                }

        ).toList();

        return new ProduitResponse(
                source.getProduitId(),
                source.getTitre(),
                source.getDescription(),
                source.getQuantiteStock(),
                source.getPrix(),
                source.getDateAjout(),
                new CategorieResponse(source.getCategorie().getCategorieId(),source.getCategorie().getNom()),
                new TypeProduitResponse(source.getTypeProduit().getTypeProduitId(),source.getTypeProduit().getNom()),
                new SingleWebsiteResponse(source.getWebsite().getWebsiteId(),source.getWebsite().getWebsiteUrl()),
                source.getCouleurs().stream().map(couleur-> new CouleurResponse(couleur.getCouleurId(), couleur.getNom())).toList(),
                null,
                images
        );

    }

    public List<ProduitResponse> convert(List<Produit> source) {
        List<ProduitResponse> produitDtos = new ArrayList<>();
        for (Produit produit : source) {

            produitDtos.add(this.convert(produit));
        }
        return produitDtos;
    }
}
