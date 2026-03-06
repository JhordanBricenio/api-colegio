package com.codej.repository;

import com.codej.dto.RegistrationSummaryDTO;
import com.codej.model.Registration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRegistrationRepository extends IGenericRepository<Registration, UUID> {

    /**
     * Returns all registrations with full details: student name, parent name, degree and education level.
     * Uses JOIN FETCH to eagerly load all related entities in a single query.
     */
    @Query("SELECT r FROM Registration r " +
           "JOIN FETCH r.student s " +
           "JOIN FETCH s.user su " +
           "JOIN FETCH s.degree d " +
           "JOIN FETCH d.educationLevel el " +
           "JOIN FETCH r.parent p " +
           "JOIN FETCH p.user pu")
    List<Registration> findAllWithDetails();

    /**
     * Returns a single registration with full details by its ID.
     */
    @Query("SELECT r FROM Registration r " +
           "JOIN FETCH r.student s " +
           "JOIN FETCH s.user su " +
           "JOIN FETCH s.degree d " +
           "JOIN FETCH d.educationLevel el " +
           "JOIN FETCH r.parent p " +
           "JOIN FETCH p.user pu " +
           "WHERE r.idRegistration = :id")
    Optional<Registration> findByIdWithDetails(@Param("id") UUID id);

    /**
     * Returns a flat summary projection of all registrations including student full name,
     * parent full name, degree and education level.
     */
    @Query("SELECT new com.codej.dto.RegistrationSummaryDTO(" +
           "r.idRegistration, r.status, r.createdAt, r.updatedAt, " +
           "su.name, su.lastname, s.code, " +
           "pu.name, pu.lastname, p.relationship, p.occupation, " +
           "d.course, d.section, el.name) " +
           "FROM Registration r " +
           "JOIN r.student s " +
           "JOIN s.user su " +
           "JOIN s.degree d " +
           "JOIN d.educationLevel el " +
           "JOIN r.parent p " +
           "JOIN p.user pu")
    List<RegistrationSummaryDTO> findAllSummary();

}
