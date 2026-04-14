package com.codej.repository;

import com.codej.dto.RegistrationDetailDTO;
import com.codej.model.Registration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
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

    // --- Existing native Kardex query (paged) kept for reference (may be unused) ---
    @Query(value = "SELECT r.id_registration as idRegistration, r.status as registrationStatus, r.created_at as registrationCreatedAt, r.updated_at as registrationUpdatedAt, " +
            "s.id_student as studentId, s.code as studentCode, CONCAT(u_s.name, ' ', u_s.last_name) as studentFullName, u_s.dni as studentDni, u_s.phone as studentPhone, u_s.email as studentEmail, " +
            "p.id_parent as parentId, CONCAT(u_p.name, ' ', u_p.last_name) as parentFullName, u_p.phone as parentPhone, u_p.dni as parentDni, p.relationship as parentRelationship, " +
            "d.id_degree as degreeId, d.course as degreeCourse, d.section as degreeSection, " +
            "el.id_education_level as educationLevelId, el.name as educationLevelName, " +
            "GROUP_CONCAT(DISTINCT CONCAT(sg.term, ':', sg.grade) SEPARATOR '; ') as gradesSummary, " +
            "COUNT(DISTINCT pay.id_payment) as paymentsCount, COALESCE(SUM(DISTINCT pay.amount_paid),0) as paymentsTotal " +
            "FROM registrations r " +
            "LEFT JOIN students s ON r.student_id = s.id_student " +
            "LEFT JOIN users u_s ON s.user_id = u_s.id_user " +
            "LEFT JOIN parents p ON r.parent_id = p.id_parent " +
            "LEFT JOIN users u_p ON p.user_id = u_p.id_user " +
            "LEFT JOIN degrees d ON s.degree_id = d.id_degree " +
            "LEFT JOIN education_levels el ON s.education_level_id = el.id_education_level " +
            "LEFT JOIN student_grades sg ON sg.student_id = s.id_student " +
            "LEFT JOIN payments pay ON pay.student_id = s.id_student " +
            "GROUP BY r.id_registration ",
            countQuery = "SELECT COUNT(DISTINCT r.id_registration) FROM registrations r " +
                    "LEFT JOIN students s ON r.student_id = s.id_student " +
                    "LEFT JOIN users u_s ON s.user_id = u_s.id_user " +
                    "LEFT JOIN parents p ON r.parent_id = p.id_parent " +
                    "LEFT JOIN users u_p ON p.user_id = u_p.id_user " +
                    "LEFT JOIN degrees d ON s.degree_id = d.id_degree " +
                    "LEFT JOIN education_levels el ON s.education_level_id = el.id_education_level " +
                    "LEFT JOIN student_grades sg ON sg.student_id = s.id_student " +
                    "LEFT JOIN payments pay ON pay.student_id = s.id_student ",
            nativeQuery = true)
    Page<Object[]> findKardexPaged(Pageable pageable);

    // --- New: native paged method using explicit limit/offset params to avoid Spring Data pagination binding issues ---
    @Query(value = "SELECT r.id_registration as idRegistration, r.status as registrationStatus, r.created_at as registrationCreatedAt, r.updated_at as registrationUpdatedAt, " +
            "s.id_student as studentId, s.code as studentCode, CONCAT(u_s.name, ' ', u_s.last_name) as studentFullName, u_s.dni as studentDni, u_s.phone as studentPhone, u_s.email as studentEmail, " +
            "p.id_parent as parentId, CONCAT(u_p.name, ' ', u_p.last_name) as parentFullName, u_p.phone as parentPhone, u_p.dni as parentDni, p.relationship as parentRelationship, " +
            "d.id_degree as degreeId, d.course as degreeCourse, d.section as degreeSection, " +
            "el.id_education_level as educationLevelId, el.name as educationLevelName, " +
            "COALESCE(JSON_ARRAYAGG(JSON_OBJECT('term', sg.term, 'grade', sg.grade)), JSON_ARRAY()) as gradesJson, " +
            "COUNT(DISTINCT pay.id_payment) as paymentsCount, COALESCE(SUM(DISTINCT pay.amount_paid),0) as paymentsTotal " +
            "FROM registrations r " +
            "LEFT JOIN students s ON r.student_id = s.id_student " +
            "LEFT JOIN users u_s ON s.user_id = u_s.id_user " +
            "LEFT JOIN parents p ON r.parent_id = p.id_parent " +
            "LEFT JOIN users u_p ON p.user_id = u_p.id_user " +
            "LEFT JOIN degrees d ON s.degree_id = d.id_degree " +
            "LEFT JOIN education_levels el ON s.education_level_id = el.id_education_level " +
            "LEFT JOIN student_grades sg ON sg.student_id = s.id_student " +
            "LEFT JOIN payments pay ON pay.student_id = s.id_student " +
            "WHERE (:studentDni IS NULL OR u_s.dni = :studentDni) " +
            "GROUP BY r.id_registration " +
            "LIMIT :limit OFFSET :offset",
            nativeQuery = true)
    List<Object[]> findKardexNative(@Param("limit") int limit, @Param("offset") int offset, @Param("studentDni") String studentDni);

    @Query(value = "SELECT COUNT(DISTINCT r.id_registration) FROM registrations r " +
            "LEFT JOIN students s ON r.student_id = s.id_student " +
            "LEFT JOIN users u_s ON s.user_id = u_s.id_user " +
            "LEFT JOIN parents p ON r.parent_id = p.id_parent " +
            "LEFT JOIN users u_p ON p.user_id = u_p.id_user " +
            "LEFT JOIN degrees d ON s.degree_id = d.id_degree " +
            "LEFT JOIN education_levels el ON s.education_level_id = el.id_education_level " +
            "LEFT JOIN student_grades sg ON sg.student_id = s.id_student " +
            "LEFT JOIN payments pay ON pay.student_id = s.id_student " +
            "WHERE (:studentDni IS NULL OR u_s.dni = :studentDni)",
            nativeQuery = true)
    long countKardex(@Param("studentDni") String studentDni);

}
