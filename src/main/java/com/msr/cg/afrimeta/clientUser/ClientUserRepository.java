package com.msr.cg.afrimeta.clientUser;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientUserRepository extends CrudRepository<ClientUser, Long> {
    Optional<ClientUser> findByUsername(String username);
}
