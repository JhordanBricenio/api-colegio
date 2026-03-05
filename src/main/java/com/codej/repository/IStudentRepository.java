package com.codej.repository;

import com.codej.model.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface IStudentRepository extends IGenericRepository<Student, UUID> {

    @Query("SELECT t FROM Student t WHERE t.user.dni = :dni")
    Optional<Student> findStudentByDni(@Param("dni") String dni);

}
