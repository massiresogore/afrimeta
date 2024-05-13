package com.msr.cg.afrimeta.clientUser;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("afrimetaJdbcClient")
public interface ClientUserRepository extends CrudRepository<ClientUser, Long> {
}
