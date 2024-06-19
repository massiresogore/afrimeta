package com.msr.cg.afrimeta.system;


import com.msr.cg.afrimeta.categorie.Categorie;
import com.msr.cg.afrimeta.categorie.CategorieService;
import com.msr.cg.afrimeta.clientUser.ClientUser;
import com.msr.cg.afrimeta.clientUser.ClientUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component // spring will pick it up this class, and initialize it as a bean
@Profile("dev")
public class DBDataInitializer implements CommandLineRunner {
    private final CategorieService categorieService;
    private final ClientUserService clientUserService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public DBDataInitializer(CategorieService categorieService, ClientUserService clientUserService) {
        this.categorieService = categorieService;
        this.clientUserService = clientUserService;
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

    }
}