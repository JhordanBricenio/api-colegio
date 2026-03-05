package com.codej.repository;

import com.codej.model.Teacher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ITeacherRepository extends IGenericRepository<Teacher, UUID> {

    @Query("SELECT t FROM Teacher t WHERE t.user.dni = :dni")
    Optional<Teacher> findTeacherByDni(@Param("dni") String dni);

}
