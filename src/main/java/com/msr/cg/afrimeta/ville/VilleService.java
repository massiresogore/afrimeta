package com.msr.cg.afrimeta.ville;

import com.msr.cg.afrimeta.system.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VilleService {

    public final JdbcClient jdbcClient;

    /**
     * <p>Le nom 'afrimetaJdbcClient' fait reférence </p>
     * <p>au nom de la fonction afrimetaJdbcClient </p>
     * <p> dans AfrimetaApplication </p>
     *
     * @param jdbcClient
     *
     */
    public VilleService(@Qualifier("afrimetaJdbcClient") JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Ville findById(String id) {
        return jdbcClient.sql("select * from ville where ville_id= :villeId ")
                .param("villeId", id)
                .query(Ville.class)
                .optional().orElseThrow(()-> new ObjectNotFoundException("ville", id));
    }

    public List<Ville> findAll(){
        return jdbcClient.sql("select * from ville where 1")
                .query(Ville.class)
                .list();
    }

    public int create(Ville ville) {
        return jdbcClient.sql("insert into ville(ville_id,nom) values(null,?) ")
                .params(List.of(ville.getNom()))
                .update();
    }

    public int update(Ville ville, String id) {
        return jdbcClient.sql("update ville set nom=? where ville_id=?")
                .params(List.of(ville.getNom(), id))
                .update();
    }

    public int deleteById(String id) {
        return jdbcClient.sql("delete from ville where ville_id=:villeId")
                .param("villeId", id)
                .update();
    }



}
