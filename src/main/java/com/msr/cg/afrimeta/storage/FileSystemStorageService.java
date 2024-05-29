package com.msr.cg.afrimeta.storage;


import com.msr.cg.afrimeta.clientUser.ClientUser;
import com.msr.cg.afrimeta.clientUser.ClientUserService;
import com.msr.cg.afrimeta.magasin.Magasin;
import com.msr.cg.afrimeta.magasin.MagasinService;
import com.msr.cg.afrimeta.magasin.dto.MagasinRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {
    private final ClientUserService clientUserService;
    private final MagasinService magasinService;

    private final Path rootLocation;

    public FileSystemStorageService(ClientUserService clientUserService, MagasinService magasinService, StorageProperties properties) {
        this.clientUserService = clientUserService;
        this.magasinService = magasinService;

        if(properties.getLocation().trim().length() == 0){
            throw new StorageException("File upload location can not be Empty.");
        }

        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void storeMagasinAndLogoImage( MagasinRequest magasinRequest) {
        MultipartFile file = magasinRequest.logoFile();
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
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

                logoMapvalue.put(filePath, fileName);
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
