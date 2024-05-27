package com.msr.cg.afrimeta;
import com.msr.cg.afrimeta.image.Image;
import com.msr.cg.afrimeta.image.ImageService;
import com.msr.cg.afrimeta.magasin.MagasinRepository;
import com.msr.cg.afrimeta.produit.Produit;
import com.msr.cg.afrimeta.produit.ProduitService;
import com.msr.cg.afrimeta.website.Website;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
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
    public CommandLineRunner commandLineRunner(ProduitService repo, ImageService imageService) {

        Website website1 = new Website(null,"http://google.com",null);
        Produit produit1 = new Produit(null, "Apple iPhone 14", "Latest model with advanced features", 50, 999.99, null, null, null, website1);
        Produit produit12 = new Produit(null, "Pc portable", "Alors on dance non ", 50, 999.99, null, null, null, website1);

        Image img1 = new Image("JPEG", "C:/images/photo1.jpg", "Photo de vacances",produit1);
        Image img2 = new Image("PNG", "C:/images/logo.png", "Logo d'entreprise",produit1);
        Image img3 = new Image("GIF", "C:/images/animation.gif", "Animation amusante",produit1);
        Image img4 = new Image("BMP", "C:/images/screenshot.bmp", "Capture d'écran",produit12);
//
        produit12.addImage(img1);
        produit12.addImage(img2);
        produit12.addImage(img3);
        produit1.addImage(img4);
      /*  repo.save(produit1);
        repo.save(produit12);*/
//        List<Image> images = imageService.selectAllImageByProduitId(Long.parseLong("1"));
//        System.out.println(images);
           /* Produit p = repo.singleProduitByProduitId("1");
        System.out.println(p);*/
//        repo.save(produit1);
//        List<Image> images = imageService.getAllImage();
        return args -> {
          //  System.out.println(images);
           /* System.out.println("done");
            System.out.printf("%s%n".repeat(2),
                    "count "+ images.size(),
                    "images list "+ images);*/
        };
    }
}
