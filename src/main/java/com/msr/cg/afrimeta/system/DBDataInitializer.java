package com.msr.cg.afrimeta.system;


import com.msr.cg.afrimeta.categorie.Categorie;
import com.msr.cg.afrimeta.categorie.CategorieService;
import com.msr.cg.afrimeta.clientUser.ClientUser;
import com.msr.cg.afrimeta.clientUser.ClientUserService;
import com.msr.cg.afrimeta.magasin.Magasin;
import com.msr.cg.afrimeta.magasin.MagasinService;
import com.msr.cg.afrimeta.produit.Produit;
import com.msr.cg.afrimeta.produit.ProduitService;
import com.msr.cg.afrimeta.website.Website;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component // spring will pick it up this class, and initialize it as a bean
//@Profile("dev")
public class DBDataInitializer implements CommandLineRunner {
    private final CategorieService categorieService;
   private final MagasinService magasinService;
    private final ClientUserService clientUserService;
    private final ProduitService produitService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public DBDataInitializer(CategorieService categorieService, MagasinService magasinService, ClientUserService clientUserService, ProduitService produitService) {
        this.categorieService = categorieService;
        this.magasinService = magasinService;
        this.clientUserService = clientUserService;
        this.produitService = produitService;
    }


    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {

    /*    ClientUser user = new ClientUser();
        user.setUsername("massires");
        user.setPassword(passwordEncoder.encode("123456789"));
        user.setEmail("massires@gmail.com");
        user.setEnable(true);
        user.setRole("user");

        clientUserService.save(user);

        Categorie categorie = new Categorie("livre");
        Categorie categorie2 = new Categorie("categorie2");
        Categorie categorie3 = new Categorie("categorie3");
        Categorie categorie4 = new Categorie("categorie4");
        Categorie categorie5 = new Categorie("categorie5");

        categorieService.save(categorie);
        categorieService.save(categorie2);
        categorieService.save(categorie3);
        categorieService.save(categorie4);
        categorieService.save(categorie5);*/

        /*Client*/
        ClientUser user1 = new ClientUser(
                null,
                "massire",
                "massire@gmail.com",
                "12345678",
                true,
                "admin user super",
                null
        );
        ClientUser user2 = new ClientUser(
                null,
                "sike",
                "sike@gmail.com",
                "12345678",
                true,
                "admin",
                null
        );
        ClientUser user3 = new ClientUser(
                null,
                "binta",
                "e@gmail.com",
                "12345678",
                true,
                "admin",
                null
        );

//       ClientUser savedUser1 =  this.clientUserService.save(user1);
//       ClientUser savedUser2 =  this.clientUserService.save(user2);
//       ClientUser savedUser3 =  this.clientUserService.save(user3);
//

        /*Magasin*/
       Map<String,String> logoMap1 = new HashMap<String,String>();
        logoMap1.put("src/test/resources/logo1.png", "logo1.png");

       Map<String,String> logoMap2 = new HashMap<String,String>();
        logoMap2.put("src/test/resources/logo2.png", "logo2.png");

       Map<String,String> logoMap3 = new HashMap<String,String>();
        logoMap3.put("src/test/resources/logo3.png", "logo3.png");

        Map<String,String> logoMap4 = new HashMap<String,String>();
        logoMap3.put("src/test/resources/logo4.png", "logo4.png");

        Map<String,String> logoMap5 = new HashMap<String,String>();
        logoMap3.put("src/test/resources/logo5.png", "logo5.png");

        Magasin magasin1 = new Magasin(null, "Supermarché BonPrix", "Un supermarché offrant une large gamme de produits alimentaires et ménagers à des prix compétitifs.", null, logoMap1);
        magasin1.setClientUser(user1);
        Magasin magasin2 = new Magasin(null, "Boulangerie Delice", "Une boulangerie artisanale proposant des pains, pâtisseries et viennoiseries faits maison.",null,  logoMap2);
        magasin2.setClientUser(user2);
        Magasin magasin3 = new Magasin(null, "Librairie PageTurner", "Une librairie indépendante avec une grande sélection de livres, magazines et fournitures de bureau.",null,  logoMap3);
        magasin3.setClientUser(user3);
        magasinService.save(magasin1);
        magasinService.save(magasin2);
        magasinService.save(magasin3);

       /*Produit*/
        Website website1 = new Website(null,"http://google.com",null);
        Website website2 = new Website(null,"http://msr.com",null);
        Website website3 = new Website(null,"http://binta.com",null);
        Website website4 = new Website(null,"http://sita.com",null);
        Website website5 = new Website(null,"http://wague.com",null);

        Produit produit1 = new Produit(null, "Apple iPhone 14", "Latest model with advanced features", 50, 999.99, null, null, null, website1);
        Produit produit2 = new Produit(null, "Samsung Galaxy S21", "High performance and sleek design", 30,  799.99, null, null, null, website2);
        Produit produit3 = new Produit(null, "Sony WH-1000XM4", "Noise cancelling wireless headphones", 100, 349.99, null, null, null, website3);
        Produit produit4 = new Produit(null, "Dell XPS 13", "Compact and powerful laptop", 20,  1299.99, null, null, null, website4);
        Produit produit5 = new Produit(null, "Nintendo Switch", "Versatile gaming console", 70,  299.99, null, null, null, website5);

        produitService.save(produit1);
        produitService.save(produit2);
        produitService.save(produit3);
        produitService.save(produit4);
        produitService.save(produit5);

    }
}