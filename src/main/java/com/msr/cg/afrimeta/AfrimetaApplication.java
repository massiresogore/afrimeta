package com.msr.cg.afrimeta;
import com.msr.cg.afrimeta.magasin.MagasinRepository;
import com.msr.cg.afrimeta.magasin.MagasinService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AfrimetaApplication {
	final MagasinRepository magasinRepository;

    public AfrimetaApplication(MagasinRepository magasinRepository) {
        this.magasinRepository = magasinRepository;
    }

    public static void main(String[] args) {
		SpringApplication.run(AfrimetaApplication.class, args);
	}

    /*@Bean
    public CommandLineRunner commandLineRunner(MagasinService magasinService) {

        return args -> {

        }
    }*/
}
