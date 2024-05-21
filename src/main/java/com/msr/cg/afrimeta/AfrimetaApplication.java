package com.msr.cg.afrimeta;
import com.msr.cg.afrimeta.magasin.Magasin;
import com.msr.cg.afrimeta.magasin.MagasinRepository;
import com.msr.cg.afrimeta.magasin.MagasinService;
import com.msr.cg.afrimeta.profile.Profile;
import com.msr.cg.afrimeta.profile.ProfileService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
//
//    @Bean
//    public CommandLineRunner commandLineRunner(MagasinService service) {
//        List<Magasin> profiles = service.findAll();
//        return args -> {
//            System.out.println(profiles);
//        };
//    }
}
