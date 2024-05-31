/*
package com.msr.cg.afrimeta.image;

import com.msr.cg.afrimeta.produit.Produit;
import com.msr.cg.afrimeta.produit.ProduitService;
import com.msr.cg.afrimeta.utils.AfrimetaCrudInterface;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService implements AfrimetaCrudInterface<Image> {

    private final ImageRepository imageRepository;
    private final ProduitService produitService;
    private final String folderPath= "/Users/esprit/www_java/projet_personnel_b3/afrimeta/afrimeta_server/src/main/resources/uploads/";

    public ImageService(ImageRepository imageRepository, ProduitService produitService) {
        this.imageRepository = imageRepository;
        this.produitService = produitService;
    }

    @Override
    public List<Image> findAll() {
        return List.of();
    }

    @Override
    public Image findById(Long id) {
        return null;
    }

    @Override
    public Image save(Image image) {
        return null;
    }

    @Override
    public Image update(Image image, Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void delete(Image image) {

    }

    public List<Image> selectAllImageByProduitId(Long produitId) {
        return this.imageRepository.selectAllImageByProduitId(produitId);
    }

   */
/* public String uploadImageToFileSystem(MultipartFile file, String produitId) throws IOException {

        Produit produit = this.produitService.findById(Long.parseLong(produitId));
        String filePath=this.folderPath+file.getOriginalFilename();

        Image image = new Image(
                file.getContentType(),
                filePath,
                file.getOriginalFilename(),
                produit
        );

       Image savedImage = this.imageRepository.save(image);

        file.transferTo(new File(filePath));

        if (savedImage != null) {
            return "Image sauvegarder avec succes";
//            return "Image sauvegarder avec succes : " + filePath;
        }
        return null;
    } *//*

    public boolean uploadImageToFileSystem(MultipartFile file, String produitId) throws IOException {

        Produit produit = this.produitService.findById(Long.parseLong(produitId));
        String filePath=this.folderPath+file.getOriginalFilename();

        Image image = new Image(
                file.getContentType(),
                filePath,
                file.getOriginalFilename(),
                produit
        );

       Image savedImage = this.imageRepository.save(image);

        file.transferTo(new File(filePath));


        return savedImage != null;
    }

    //recupère une image par nom
    public List<byte[]> downloadImageFromFileSystem(String fileName) throws IOException {
//        Optional<FileData> fileData = fileDataRepository.findByName(fileName);
//        Image image = this.imageRepository.findByName(fileName);
        List<Image> images = this.imageRepository.findByName(fileName);

        List<String>  filesPath = new ArrayList<>();
        List<byte[]> imagesListByte = new ArrayList<>();

        for (Image image : images) {
            //Récupère le chemin du fichier
            String filePath=image.getFilePath();
            //lis le ficher se trouvant dans le chemin
            byte[] imageByte = Files.readAllBytes(new File(filePath).toPath());
            imagesListByte.add(imageByte);
        }

        return imagesListByte;
    }
   */
/* //recupère une image par nom
    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
//        Optional<FileData> fileData = fileDataRepository.findByName(fileName);
        Image image = this.imageRepository.findByName(fileName);
        //Récupère le chemin du fichier
        String filePath=image.getFilePath();
        //lis le ficher se trouvant dans le chemin
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }
*//*

    //image par produit id
    public List<byte[]> downloadImagesFromFileSystem(Long produitId) throws IOException {
        List<Image> fileDatas = this.selectAllImageByProduitId(produitId);
        List<byte[]> stringList = new ArrayList<>();

        for(Image fileData: fileDatas){
            String filePath=fileData.getFilePath();
            byte[] image = Files.readAllBytes(new File(filePath).toPath());
            stringList.add(image);
        }

        return stringList;

    }

}
*/
