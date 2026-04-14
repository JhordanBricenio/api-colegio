package com.codej.repository;

import com.codej.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface IUserRepository extends IGenericRepository<User, UUID> {

    boolean existsByEmail(String email);
    boolean existsByDni(String dni);
    User findByDni(String dni);
    User findByEmail(String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.role r WHERE u.dni = :dni")
    User findByDniWithRole(@Param("dni") String dni);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.role r WHERE u.email = :email")
    User findByEmailWithRole(@Param("email") String email);
}
