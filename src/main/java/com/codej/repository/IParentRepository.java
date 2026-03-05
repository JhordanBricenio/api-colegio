package com.codej.repository;

import com.codej.model.Parent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface IParentRepository extends IGenericRepository<Parent, UUID> {

    @Query("SELECT t FROM Parent t WHERE t.user.dni = :dni")
    Optional<Parent> findParentByDni(@Param("dni") String dni);

}
