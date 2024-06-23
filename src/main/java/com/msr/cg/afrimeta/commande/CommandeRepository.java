package com.msr.cg.afrimeta.commande;

import com.msr.cg.afrimeta.clientUser.ClientUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {

    @Query(value = "select * from commande where commande.user_id=?",nativeQuery = true)
    List<Commande> retriveCommandeByIdClient(Long client);

}
