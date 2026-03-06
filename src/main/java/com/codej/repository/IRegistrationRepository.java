package com.codej.repository;

import com.codej.model.Registration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface IRegistrationRepository extends IGenericRepository<Registration, UUID> {

    @Query("SELECT DISTINCT r FROM Registration r " +
            "JOIN FETCH r.student s " +
            "JOIN FETCH s.user su " +
            "JOIN FETCH su.role sur " +
            "JOIN FETCH s.degree sd " +
            "JOIN FETCH sd.educationLevel sel " +
            "JOIN FETCH s.educationLevel se " +
            "JOIN FETCH se.management sem " +
            "JOIN FETCH r.parent p " +
            "JOIN FETCH p.user pu " +
            "JOIN FETCH pu.role pur " +
            "LEFT JOIN FETCH p.student ps")
    List<Registration> findAllWithDetails();

    @Query(value = "SELECT DISTINCT r FROM Registration r " +
            "JOIN FETCH r.student s " +
            "JOIN FETCH s.user su " +
            "JOIN FETCH su.role sur " +
            "JOIN FETCH s.degree sd " +
            "JOIN FETCH sd.educationLevel sel " +
            "JOIN FETCH s.educationLevel se " +
            "JOIN FETCH se.management sem " +
            "JOIN FETCH r.parent p " +
            "JOIN FETCH p.user pu " +
            "JOIN FETCH pu.role pur " +
            "LEFT JOIN FETCH p.student ps",
            countQuery = "SELECT COUNT(r) FROM Registration r")
    Page<Registration> findAllWithDetailsPaged(Pageable pageable);
}
