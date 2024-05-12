package com.msr.cg.afrimeta;

import com.msr.cg.afrimeta.ville.Ville;
import com.msr.cg.afrimeta.ville.VilleService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.simple.JdbcClient;

import javax.sql.DataSource;
import java.util.List;

@SpringBootApplication
public class AfrimetaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AfrimetaApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(VilleService villeService) {
		//Ville ville = villeService.findById("12");
		//List<Ville> villes = villeService.findAll();
		//Ville ville = new Ville("Ouganda update");
		//int villeResult = villeService.create(ville);
		//int villeResult = villeService.update(ville,"3");
		int villeResult = villeService.deleteById("3");
		return args -> {
			//System.out.println("La ville rechercher est: " + ville);
			//System.out.println(villes);
			//System.out.println(villeResult);

			//System.out.println("done !");
		};
	}

	@Bean
	JdbcClient afrimetaJdbcClient(@Qualifier("afrimetaDataSource") DataSource afrimetaDataSource){
		return JdbcClient.create(afrimetaDataSource);
	}

}
