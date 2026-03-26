package com.codej.repository;

import com.codej.dto.RegistrationDetailDTO;
import com.codej.model.Registration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface IRegistrationRepository extends IGenericRepository<Registration, UUID> {

    @Query("SELECT new com.codej.dto.RegistrationDetailDTO(r.idRegistration, r.status, r.createdAt, r.updatedAt, " +
            "s.idStudent, CONCAT(uS.name, ' ', uS.lastname), s.code, uS.dni, " +
            "p.idParent, CONCAT(uP.name, ' ', uP.lastname), uP.phone, uP.dni, p.relationship, " +
            "d.idDegree, d.course, d.section, " +
            "el.idEducationLevel, el.name) " +
            "FROM Registration r " +
            "LEFT JOIN r.student s " +
            "LEFT JOIN s.user uS " +
            "LEFT JOIN r.parent p " +
            "LEFT JOIN p.user uP " +
            "LEFT JOIN s.degree d " +
            "LEFT JOIN s.educationLevel el " +
            "WHERE r.idRegistration = :id")
    RegistrationDetailDTO findDetailById(@Param("id") UUID id);

    @Query(value = "SELECT new com.codej.dto.RegistrationDetailDTO(r.idRegistration, r.status, r.createdAt, r.updatedAt, " +
            "s.idStudent, CONCAT(uS.name, ' ', uS.lastname), s.code, uS.dni, " +
            "p.idParent, CONCAT(uP.name, ' ', uP.lastname), uP.phone, uP.dni, p.relationship, " +
            "d.idDegree, d.course, d.section, " +
            "el.idEducationLevel, el.name) " +
            "FROM Registration r " +
            "LEFT JOIN r.student s " +
            "LEFT JOIN s.user uS " +
            "LEFT JOIN r.parent p " +
            "LEFT JOIN p.user uP " +
            "LEFT JOIN s.degree d " +
            "LEFT JOIN s.educationLevel el",
            countQuery = "SELECT count(r) FROM Registration r")
    Page<RegistrationDetailDTO> findAllWithDetailsPaged(Pageable pageable);
}
