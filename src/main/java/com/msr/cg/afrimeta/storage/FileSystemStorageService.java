package com.msr.cg.afrimeta.storage;


import com.msr.cg.afrimeta.categorie.Categorie;
import com.msr.cg.afrimeta.clientUser.ClientUser;
import com.msr.cg.afrimeta.clientUser.ClientUserService;
import com.msr.cg.afrimeta.couleur.Couleur;
import com.msr.cg.afrimeta.image.Image;
import com.msr.cg.afrimeta.magasin.Magasin;
import com.msr.cg.afrimeta.magasin.MagasinService;
import com.msr.cg.afrimeta.magasin.dto.MagasinRequest;
import com.msr.cg.afrimeta.produit.Produit;
import com.msr.cg.afrimeta.produit.ProduitRepository;
import com.msr.cg.afrimeta.produit.dto.dto.ProduitRequest;
import com.msr.cg.afrimeta.system.exception.StorageException;
import com.msr.cg.afrimeta.system.exception.StorageFileNotFoundException;
import com.msr.cg.afrimeta.taille.Taille;
import com.msr.cg.afrimeta.typeproduit.TypeProduit;
import com.msr.cg.afrimeta.website.Website;
import com.msr.cg.afrimeta.website.WebsiteService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {
    private final ClientUserService clientUserService;
    private final MagasinService magasinService;
    private final WebsiteService websiteService;
    private final ProduitRepository produitRepository;

    private final Path rootLocation;

    public FileSystemStorageService(ClientUserService clientUserService, MagasinService magasinService, WebsiteService websiteService, ProduitRepository produitRepository, StorageProperties properties) {
        this.clientUserService = clientUserService;
        this.magasinService = magasinService;
        this.websiteService = websiteService;
        this.produitRepository = produitRepository;
        if(properties.getLocation().trim().length() == 0){
            throw new StorageException("File upload location can not be Empty.");
        }

        this.rootLocation = Paths.get(properties.getLocation());
    }


    @Override
    public void storeProduitAndImage(ProduitRequest produitRequest) {

        //Gestion StorageImage
        MultipartFile file = produitRequest.image();


        try {
            if(file.isEmpty()){
                throw new StorageException("File upload location can not be Empty.");
            }

            Path destinationFile = this.rootLocation.resolve(
                            Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }

            try (InputStream inputStream = file.getInputStream()) {
                Produit produit = new Produit();
                produit.setTitre(produitRequest.titre());
                produit.setDescription(produitRequest.description());
                produit.setQuantiteStock(produitRequest.quantiteStock());
                produit.setPrix(produitRequest.prix());

                //Catégorie
                Categorie defaultCategorie = new Categorie("default categorie");

                //Type produit and taille
                TypeProduit defaultTypeProduit = new TypeProduit("default type produit");
                Taille defaultTaille = new Taille("default taille");
                defaultTypeProduit.addTaille(defaultTaille);

                //Couleur
                Couleur defaultCouleur = new Couleur("default couleur");


                produit.setCategorie(defaultCategorie);
                produit.setTypeProduit(defaultTypeProduit);
                // produit.addImage(defaultImage);
                produit.addCouleur(defaultCouleur);

                //Find Website
                Website website = this.websiteService.findById(Long.valueOf(produitRequest.websiteId()));
                produit.setWebsite(website);

                //Remove space in string
                String filePath = String.valueOf(destinationFile).replace(" ","");
                String fileName = file.getOriginalFilename().replace(" ","");

                //Image
                Image image = new Image(file.getContentType(),String.valueOf(destinationFile),file.getOriginalFilename());
                produit.addImage(image);

                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
                //save Produit
                System.out.println("saved to " + destinationFile.toAbsolutePath());
                this.produitRepository.save(produit);
            }

        }catch (IOException e){
            throw new StorageException("Fi le upload location can not be Empty.");
        }
    }

    @Override
    public void storeMagasinAndLogoImage( MagasinRequest magasinRequest) {
        MultipartFile file = magasinRequest.logoFile();
        try {
            if (file.isEmpty()) {
                throw new StorageException("le logo est obligatoire.");
            }
            Path destinationFile = this.rootLocation.resolve(
                            Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();


            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Magasin magasin = new Magasin();
                magasin.setLibele(magasinRequest.libele());
                magasin.setDescription(magasinRequest.description());

                //Find client
                ClientUser clientFound =  this.clientUserService.findById(Long.parseLong(magasinRequest.clientId()));
                magasin.setClientUser(clientFound);

                //Logo
                Map<String, String> logoMapvalue = new HashMap<>();
                //Remove space in string
                String filePath = String.valueOf(destinationFile).replace(" ","");
                String fileName = file.getOriginalFilename().replace(" ","");

                logoMapvalue.put(filePath, file.getOriginalFilename());
                magasin.setLogo(logoMapvalue);

                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);

                this.magasinService.save(magasin);
            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }



    @Override
    public void store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            Path destinationFile = this.rootLocation.resolve(
                            Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();
            System.out.printf("Storing file: %s%n".repeat(3),
                    "destinationFile: ",destinationFile,
                    "destinationFile parent",destinationFile.getParent(),
                    "rootLocation to AbsolutePath",this.rootLocation.toAbsolutePath());
            //response
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                System.out.println(inputStream);
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    //Récupere les noms de toutes les images dans le path dans un tableau
    //exp: [fernando-alvarez-rodriguez-M7GddPqJowg-unsplash.jpg, manuel-moreno-DGa0LQ0yDPc-unsplash.jpg]
    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

  /*  public void loadOneFile(String filename) {
        Path  startPath =  rootLocation.resolve(filename);
        try (Stream<Path> paths = Files.walk(startPath)) {
            paths.filter(Files::isRegularFile)
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            System.out.println(file);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void deleteOne(String filename) {
        FileSystemUtils.deleteRecursively(new File(filename));
    }


    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
