package com.msr.cg.afrimeta;
import com.msr.cg.afrimeta.magasin.MagasinRepository;
import com.msr.cg.afrimeta.storage.FileSystemStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.nio.file.Path;
import java.util.List;

@SpringBootApplication
public class AfrimetaApplication {
	final MagasinRepository magasinRepository;

    public AfrimetaApplication(MagasinRepository magasinRepository) {
        this.magasinRepository = magasinRepository;
    }

    public static void main(String[] args) {
		SpringApplication.run(AfrimetaApplication.class, args);
	}

    @Bean
    public CommandLineRunner commandLineRunner(FileSystemStorageService fileSystemStorageService) {
       // List<Path> filesPath = fileSystemStorageService.loadAll().toList();
            //fileSystemStorageService.deleteAll();
        return args -> {
           // System.out.println(filesPath);
        };
    }

}
