package com.msr.cg.afrimeta.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    @Query(value = "select * from profile p where p.user_id=? ", nativeQuery = true)
    Profile findByUserId(String userId);
}
