package com.msr.cg.afrimeta;

import com.msr.cg.afrimeta.embaddable.ContenirRepository;
import com.msr.cg.afrimeta.magasin.MagasinRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AfrimetaApplication {
	final MagasinRepository magasinRepository;
    final ContenirRepository contenirRepository;

    public AfrimetaApplication(MagasinRepository magasinRepository, ContenirRepository contenirRepository) {
        this.magasinRepository = magasinRepository;
        this.contenirRepository = contenirRepository;
    }

    public static void main(String[] args) {
		SpringApplication.run(AfrimetaApplication.class, args);
	}
    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            System.out.println("done");
        };
    }

}
