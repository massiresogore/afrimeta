package com.msr.cg.afrimeta;

import com.msr.cg.afrimeta.adresse.Adresse;
import com.msr.cg.afrimeta.adresse.AdresseRepository;
import com.msr.cg.afrimeta.ville.Ville;
import com.msr.cg.afrimeta.ville.VilleRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
public class AfrimetaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AfrimetaApplication.class, args);
	}

	/*@Bean
	public CommandLineRunner commandLineRunner(AdresseRepository villeRepository) {

		//Ville ville = villeService.findById("12");
		//List<Ville> villes = villeService.findAll();
		Ville paris = new Ville("Infondo Congo");
		Adresse adresse1 = new Adresse("40",77440,paris);

		//Ville villeResult = villeService.save(ville);
		//int villeResult = villeService.update(ville,"3");
		//int villeResult = villeService.deleteById("3");
		Adresse infondo = villeRepository.save(adresse1);
		return args -> {
			//System.out.println("La ville rechercher est: " + ville);
			//System.out.println(villes);
			//System.out.println(villeResult);
//			System.out.print(ville);
			//System.out.println("done !");
			System.out.print(infondo);
		};
	}
*/
	@Bean
	org.springframework.jdbc.core.simple.JdbcClient afrimetaJdbcClient(@Qualifier("afrimetaDataSource") DataSource afrimetaDataSource){
		return org.springframework.jdbc.core.simple.JdbcClient.create(afrimetaDataSource);
	}

}
