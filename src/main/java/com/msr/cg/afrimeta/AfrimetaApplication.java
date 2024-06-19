package com.msr.cg.afrimeta;
import com.msr.cg.afrimeta.categorie.Categorie;
import com.msr.cg.afrimeta.categorie.CategorieService;
import com.msr.cg.afrimeta.clientUser.ClientUser;
import com.msr.cg.afrimeta.clientUser.ClientUserService;
import com.msr.cg.afrimeta.commande.Commande;
import com.msr.cg.afrimeta.commande.CommandeService;
import com.msr.cg.afrimeta.embaddable.Contenir;
import com.msr.cg.afrimeta.embaddable.ContenirRepository;
import com.msr.cg.afrimeta.embaddable.ProduitCommandeKey;
import com.msr.cg.afrimeta.facture.Facture;
import com.msr.cg.afrimeta.facture.FactureService;
import com.msr.cg.afrimeta.facture.dto.FactureDto;
import com.msr.cg.afrimeta.magasin.MagasinRepository;
import com.msr.cg.afrimeta.produit.Produit;
import com.msr.cg.afrimeta.produit.ProduitService;
import com.msr.cg.afrimeta.storage.FileSystemStorageService;
import com.msr.cg.afrimeta.website.Website;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    public CommandLineRunner commandLineRunner(
            FileSystemStorageService fileSystemStorageService,
            ProduitService produitService, FactureService factureService,
            CommandeService commandeService,
            CategorieService categorieService,
            ClientUserService clientUserService) {
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

        /*Test Commande*/
        //ClientUser
//        ClientUser clientUser = new ClientUser("emmano","m@gmail.com","MZMZMZMZMZMZZM",true,"ADMIN USER",null);
//
//        //Facture
//        Facture facture = new Facture( LocalDate.now(),22,22,2);
//
//        Commande commande1 = new Commande(null,LocalDate.now(),22,"33 rue bandas",22,7,clientUser,facture);
//
//        Commande newCommande = commandeService.save(commande1);

        /*Produit avec Commande*/
//        ClientUser clientUser = clientUserService.findById(Long.parseLong("1"));
//
//        List<Produit> produits = new ArrayList<>();
//        Produit produit1 = produitService.findById(Long.parseLong("1"));
//        Produit produit2 = produitService.findById(Long.parseLong("2"));
//        Produit produit3 = produitService.findById(Long.parseLong("3"));
//
//        produits.add(produit1);
//        produits.add(produit2);
//        produits.add(produit3);
//
//        Commande commande1 = new Commande();
//        commande1.setClientUser(clientUser);
//        commande1.setCommandeTotal(produits.stream().map(produit -> produit.getPrix()).reduce((double) 0,(subtotal, element)-> subtotal+element));
//        commande1.setNombreProduit(produits.size());
//        commande1.setAdresse("40 rue jean jaurres");
//        commande1.setPrixTotal(produits.stream().map(produit -> produit.getPrix()).reduce((double) 0,(subtotal, element)-> subtotal+element));

       //Commande newCommande = commandeService.save(commande1);

//        for(Produit produit: produits) {
//            Contenir contenir1 = new Contenir();
//            contenir1.setCommande(newCommande);
//            contenir1.setQuantite(produit.getQuantiteStock());
//            contenir1.setProduit(produit);
//            ProduitCommandeKey produitCommandeKey = new ProduitCommandeKey(produit.getProduitId(), newCommande.getCommandeId());
//            contenir1.setId(produitCommandeKey);
//            //contenirRepository.save(contenir1);
//        }



        return args -> {
          //  System.out.println(commande1.getCommandeTotal());
            System.out.println("done");

        };
    }

}
