package com.msr.cg.afrimeta.storage;


import com.msr.cg.afrimeta.magasin.dto.MagasinRequest;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    void storeMagasinAndLogoImage(MagasinRequest magasinRequest);
    void store(MultipartFile file);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();

    void deleteOne(String filename);

}