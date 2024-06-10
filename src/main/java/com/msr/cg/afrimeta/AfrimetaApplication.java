package com.msr.cg.afrimeta;
import com.msr.cg.afrimeta.facture.Facture;
import com.msr.cg.afrimeta.facture.FactureService;
import com.msr.cg.afrimeta.facture.dto.FactureDto;
import com.msr.cg.afrimeta.magasin.MagasinRepository;
import com.msr.cg.afrimeta.produit.ProduitService;
import com.msr.cg.afrimeta.storage.FileSystemStorageService;
import com.msr.cg.afrimeta.website.Website;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

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
    public CommandLineRunner commandLineRunner(FileSystemStorageService fileSystemStorageService, ProduitService produitService, FactureService factureService) {
      /*  Website website1 = new Website(null,"http://google.com",null);
        Website website2 = new Website(null,"http://google.com",null);
        Website website3 = new Website(null,"http://google.com",null);
        Website website4 = new Website(null,"http://google.com",null);
        Website website5 = new Website(null,"http://google.com",null);
*/
       /* Produith produit1 = new Produith(null, "Apple iPhone 14", "Latest model with advanced features", 50, 999.99, null, null, null, website1);
        Produith produit2 = new Produith(null, "Samsung Galaxy S21", "High performance and sleek design", 30,  799.99, null, null, null, website2);
        Produith produit3 = new Produith(null, "Sony WH-1000XM4", "Noise cancelling wireless headphones", 100, 349.99, null, null, null, website3);
        Produith produit4 = new Produith(null, "Dell XPS 13", "Compact and powerful laptop", 20,  1299.99, null, null, null, website4);
        Produith produit5 = new Produith(null, "Nintendo Switch", "Versatile gaming console", 70,  299.99, null, null, null, website5);
    */
     /*   produitService.save(produit1);
        produitService.save(produit2);
        produitService.save(produit3);
        produitService.save(produit4);
        produitService.save(produit5);*/

        //Produit produit = produitService.singleProduitByProduitId("1");
//        System.out.println( "produit :"+produit.toString());
//        System.out.println("categorie :"+produit.getCategorie().toString());
//        System.out.println(produit.getTypeProduit().toString());
//        System.out.println( "images "+ produit.getImages().toString());
//
      /*  FactureDto factureDto2 = new FactureDto(null, LocalDate.now(), 22, 22, 2);
        //Object
        Facture facture = new Facture();
        facture.setFactureId(null);
        facture.setFactureDate(factureDto2.factureDate());
        facture.setTotalHorsTaxe(factureDto2.totalHorsTaxe());
        facture.setTotalToutTaxeComprise(factureDto2.totalToutTaxeComprise());
        facture.setTotalTva(factureDto2.totalTva());

        Facture savedFacture = factureService.save(facture);*/
        return args -> {
            System.out.println("done");

        };
    }

}
