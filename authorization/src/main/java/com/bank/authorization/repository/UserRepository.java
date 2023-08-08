package com.bank.authorization.repository;

import com.bank.authorization.model.Users;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

//import javax.transaction.Transactional;
//import java.beans.Transient;
//import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    //@Transactional
    //@Modifying
    //@Query(value = "INSERT INTO Users (id, password, role, profile_id) VALUES (:id, :password, :role, :profileId)", nativeQuery = true)
    //void saveNoJPA(@Param("id") Long id,
    //                   @Param("password") String password,
    //                   @Param("role") String role,
    //                   @Param("profileId") int profileId);

    @Query(value = "SELECT * FROM auth.users WHERE profile_id = :username", nativeQuery = true)
    Optional<Users> getAllByProfileId(@Param("username") int username);
}
