package com.codej.service.impl;


import com.codej.dto.KardexDTO;
import com.codej.dto.RegistrationDetailDTO;
import com.codej.model.Registration;
import com.codej.repository.IGenericRepository;
import com.codej.repository.IRegistrationRepository;
import com.codej.service.IRegistrationService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl extends CRUDGenericImpl<Registration, UUID> implements IRegistrationService {

    private final IRegistrationRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private static final String KARDEX_SQL =
            "SELECT r.id_registration AS idRegistration, " +
            "r.status AS registrationStatus, " +
            "r.created_at AS registrationCreatedAt, " +
            "r.updated_at AS registrationUpdatedAt, " +
            "s.id_student AS studentId, " +
            "s.code AS studentCode, " +
            "CONCAT(u_s.name, ' ', u_s.last_name) AS studentFullName, " +
            "u_s.dni AS studentDni, " +
            "u_s.phone AS studentPhone, " +
            "u_s.email AS studentEmail, " +
            "p.id_parent AS parentId, " +
            "CONCAT(u_p.name, ' ', u_p.last_name) AS parentFullName, " +
            "u_p.phone AS parentPhone, " +
            "u_p.dni AS parentDni, " +
            "p.relationship AS parentRelationship, " +
            "d.id_degree AS degreeId, " +
            "d.course AS degreeCourse, " +
            "d.section AS degreeSection, " +
            "el.id_education_level AS educationLevelId, " +
            "el.name AS educationLevelName, " +
            "GROUP_CONCAT(DISTINCT CONCAT(sg.term, ':', sg.grade) ORDER BY sg.term SEPARATOR '; ') AS gradesSummary, " +
            "COUNT(DISTINCT pay.id_payment) AS paymentsCount, " +
            "COALESCE(SUM(pay.amount_paid), 0) AS paymentsTotal " +
            "FROM registrations r " +
            "LEFT JOIN students s ON r.student_id = s.id_student " +
            "LEFT JOIN users u_s ON s.user_id = u_s.id_user " +
            "LEFT JOIN parents p ON r.parent_id = p.id_parent " +
            "LEFT JOIN users u_p ON p.user_id = u_p.id_user " +
            "LEFT JOIN degrees d ON s.degree_id = d.id_degree " +
            "LEFT JOIN education_levels el ON s.education_level_id = el.id_education_level " +
            "LEFT JOIN student_grades sg ON sg.student_id = s.id_student " +
            "LEFT JOIN payments pay ON pay.student_id = s.id_student " +
            "GROUP BY r.id_registration";

    private static final String KARDEX_COUNT_SQL =
            "SELECT COUNT(DISTINCT r.id_registration) FROM registrations r";

    @Override
    protected IGenericRepository<Registration, UUID> getRepository() {
        return userRepository;
    }

    @Override
    public Registration saveRegistration(Registration user) throws Exception {

        return userRepository.save(user);
    }
    
    @Override
    public Page<Registration> findAllPaged(Pageable pageable) throws Exception {
        return userRepository.findAll(pageable);
    }

    @Override
    public Page<RegistrationDetailDTO> findAllWithDetails(Pageable pageable) throws Exception {
        return userRepository.findAllWithDetailsPaged(pageable);
    }

    @Override
    public RegistrationDetailDTO findDetailById(UUID id) throws Exception {
        return userRepository.findDetailById(id);
    }

    @Override
    public Page<KardexDTO> findKardexPaged(Pageable pageable) throws Exception {
        Query query = entityManager.createNativeQuery(KARDEX_SQL);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        @SuppressWarnings("unchecked")
        List<Object[]> rows = query.getResultList();

        List<KardexDTO> dtos = rows.stream()
                .map(this::mapRowToKardexDTO)
                .collect(Collectors.toList());

        Query countQuery = entityManager.createNativeQuery(KARDEX_COUNT_SQL);
        long total = ((Number) countQuery.getSingleResult()).longValue();

        return new PageImpl<>(dtos, pageable, total);
    }

    private KardexDTO mapRowToKardexDTO(Object[] row) {
        return new KardexDTO(
                uuidStringFromColumn(row[0]),
                toBool(row[1]),
                toStr(row[2]),
                toStr(row[3]),
                uuidStringFromColumn(row[4]),
                toStr(row[5]),
                toStr(row[6]),
                toStr(row[7]),
                toStr(row[8]),
                toStr(row[9]),
                uuidStringFromColumn(row[10]),
                toStr(row[11]),
                toStr(row[12]),
                toStr(row[13]),
                toStr(row[14]),
                uuidStringFromColumn(row[15]),
                toStr(row[16]),
                toStr(row[17]),
                uuidStringFromColumn(row[18]),
                toStr(row[19]),
                toStr(row[20]),
                toLong(row[21]),
                toBigDecimal(row[22])
        );
    }

    private String uuidStringFromColumn(Object val) {
        if (val == null) return null;
        if (val instanceof String) return (String) val;
        if (val instanceof byte[]) {
            byte[] bytes = (byte[]) val;
            ByteBuffer bb = ByteBuffer.wrap(bytes);
            return new UUID(bb.getLong(), bb.getLong()).toString();
        }
        return val.toString();
    }

    private Boolean toBool(Object val) {
        if (val == null) return false;
        if (val instanceof Boolean) return (Boolean) val;
        if (val instanceof Number) return ((Number) val).intValue() != 0;
        return Boolean.parseBoolean(val.toString());
    }

    private String toStr(Object val) {
        return val != null ? val.toString() : null;
    }

    private Long toLong(Object val) {
        if (val == null) return 0L;
        if (val instanceof Number) return ((Number) val).longValue();
        return Long.parseLong(val.toString());
    }

    private BigDecimal toBigDecimal(Object val) {
        if (val == null) return BigDecimal.ZERO;
        if (val instanceof BigDecimal) return (BigDecimal) val;
        if (val instanceof Number) return BigDecimal.valueOf(((Number) val).doubleValue());
        return new BigDecimal(val.toString());
    }
}

