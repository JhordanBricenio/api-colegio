package com.codej.controller;


import com.codej.dto.KardexDTO;
import com.codej.dto.RegistrationDTO;
import com.codej.dto.RegistrationDetailDTO;
import com.codej.mapper.RegistrationMapper;
import com.codej.model.Registration;
import com.codej.service.IRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.Clob;
import java.io.Reader;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.codej.constants.ApiConstants.ID_IN_PATH;
import static com.codej.constants.ApiConstants.REGISTRATION_BASE;

@RestController
@RequestMapping(REGISTRATION_BASE)
@RequiredArgsConstructor
public class RegistrationController {

    private final IRegistrationService registrationService;
    private final RegistrationMapper registrationMapper;

    @GetMapping
    public ResponseEntity< List<RegistrationDTO>> findAll() throws Exception {
        registrationMapper.toRegistrationDTOList(registrationService.findAll());
        return ResponseEntity.ok(registrationMapper.toRegistrationDTOList(registrationService.findAll()));
    }

    @GetMapping("/paged/{page}")
    public Page< RegistrationDTO> findAllPaged(@PathVariable Integer page) throws Exception {
        Pageable pageable = PageRequest.of(page, 8);
        Page<Registration> registrationPage = registrationService.findAllPaged(pageable);
        return registrationPage.map(registrationMapper::toRegistrationDTO);
    }

    @GetMapping("/details/paged/{page}")
    public Page< RegistrationDetailDTO> findAllDetailsPaged(@PathVariable Integer page) throws Exception {
        Pageable pageable = PageRequest.of(page, 8);
        return registrationService.findAllWithDetails(pageable);
    }

    @PostMapping
    public ResponseEntity<RegistrationDTO> save(@Valid @RequestBody RegistrationDTO registrationDTO) throws Exception {
        Registration registration= registrationMapper.toRegistrationEntity(registrationDTO);
        Registration savedRegistration = registrationService.saveRegistration(registration);
        return ResponseEntity.status(HttpStatus.CREATED).body(registrationMapper.toRegistrationDTO(savedRegistration));
    }
    @GetMapping(ID_IN_PATH)
    public ResponseEntity<RegistrationDTO> findById(@PathVariable UUID id) throws Exception {
        return ResponseEntity.ok(registrationMapper.toRegistrationDTO(registrationService.findById(id)));
    }

    @GetMapping("/details" + ID_IN_PATH)
    public ResponseEntity<RegistrationDetailDTO> findDetailById(@PathVariable UUID id) throws Exception {
        return ResponseEntity.ok(registrationService.findDetailById(id));
    }
//    @PostMapping("/dni")
//    public ResponseEntity<RegistrationDTO> findByDni(@RequestBody DniRequest dniRequest) throws Exception {
//        return ResponseEntity.ok(registrationMapper.toRegistrationDTO(registrationService.findByDni(dniRequest.getDni())));
//    }

    @PatchMapping(ID_IN_PATH)
    public ResponseEntity<RegistrationDTO> update(@Valid @RequestBody RegistrationDTO registrationDTO,@PathVariable UUID id) throws Exception {
        Registration registration = registrationMapper.toRegistrationEntity(registrationDTO);
        Registration updatedRegistration = registrationService.update(registration, id);
        return ResponseEntity.ok(registrationMapper.toRegistrationDTO(updatedRegistration));
    }
    @DeleteMapping(ID_IN_PATH)
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws Exception {
        registrationService.delete(id);
        return  ResponseEntity.noContent().build();
    }

    // --- New kardex endpoint with optional DNI filter ---
    @GetMapping("/kardex/paged/{page}")
    public Page<KardexDTO> findKardexPaged(@PathVariable Integer page, @RequestParam(required = false) String studentDni) throws Exception {
        Pageable pageable = PageRequest.of(page, 8);
        Page<Object[]> pageResult = registrationService.findKardexPaged(pageable, studentDni);

        List<KardexDTO> content = new ArrayList<>();
        for (Object[] row : pageResult.getContent()) {
            // Map each column to the DTO (careful with nulls and types)
            String idRegistration = toUuidString(row[0]);
            String registrationStatus = row[1] != null ? row[1].toString() : null;
            String registrationCreatedAt = row[2] != null ? row[2].toString() : null;
            String registrationUpdatedAt = row[3] != null ? row[3].toString() : null;

            String studentId = toUuidString(row[4]);
            String studentCode = row[5] != null ? row[5].toString() : null;
            String studentFullName = row[6] != null ? row[6].toString() : null;
            String studentDniRes = row[7] != null ? row[7].toString() : null;
            String studentPhone = row[8] != null ? row[8].toString() : null;
            String studentEmail = row[9] != null ? row[9].toString() : null;

            String parentId = toUuidString(row[10]);
            String parentFullName = row[11] != null ? row[11].toString() : null;
            String parentPhone = row[12] != null ? row[12].toString() : null;
            String parentDni = row[13] != null ? row[13].toString() : null;
            String parentRelationship = row[14] != null ? row[14].toString() : null;

            String degreeId = toUuidString(row[15]);
            String degreeCourse = row[16] != null ? row[16].toString() : null;
            String degreeSection = row[17] != null ? row[17].toString() : null;

            String educationLevelId = toUuidString(row[18]);
            String educationLevelName = row[19] != null ? row[19].toString() : null;

            // gradesJson may come as byte[] from JDBC for JSON column, decode as UTF-8
            String gradesJson = dbBytesToString(row[20]);

            Integer paymentsCount = row[21] != null ? ((Number) row[21]).intValue() : 0;
            BigDecimal paymentsTotal = row[22] != null ? new BigDecimal(row[22].toString()) : BigDecimal.ZERO;

            KardexDTO dto = new KardexDTO(
                    idRegistration, registrationStatus, registrationCreatedAt, registrationUpdatedAt,
                    studentId, studentCode, studentFullName, studentDniRes, studentPhone, studentEmail,
                    parentId, parentFullName, parentPhone, parentDni, parentRelationship,
                    degreeId, degreeCourse, degreeSection,
                    educationLevelId, educationLevelName,
                    gradesJson,
                    paymentsCount, paymentsTotal
            );
            content.add(dto);
        }

        return new PageImpl<>(content, pageable, pageResult.getTotalElements());
    }

    private String toUuidString(Object dbValue) {
        if (dbValue == null) return null;
        try {
            if (dbValue instanceof UUID) return dbValue.toString();
            if (dbValue instanceof String) return (String) dbValue;
            if (dbValue instanceof byte[]) {
                byte[] bytes = (byte[]) dbValue;
                if (bytes.length == 16) {
                    ByteBuffer bb = ByteBuffer.wrap(bytes);
                    long high = bb.getLong();
                    long low = bb.getLong();
                    return new UUID(high, low).toString();
                }
                // fallback: hex
                StringBuilder sb = new StringBuilder();
                for (byte b : bytes) sb.append(String.format("%02x", b));
                return sb.toString();
            }
            if (dbValue instanceof Blob) {
                Blob blob = (Blob) dbValue;
                int len = (int) blob.length();
                byte[] bytes = blob.getBytes(1, len);
                if (bytes.length == 16) {
                    ByteBuffer bb = ByteBuffer.wrap(bytes);
                    long high = bb.getLong();
                    long low = bb.getLong();
                    return new UUID(high, low).toString();
                }
                StringBuilder sb = new StringBuilder();
                for (byte b : bytes) sb.append(String.format("%02x", b));
                return sb.toString();
            }
            if (dbValue.getClass().isArray()) {
                // generic array fallback
                Object[] arr = (Object[]) dbValue;
                if (arr.length == 16 && arr[0] instanceof Byte) {
                    byte[] bytes = new byte[arr.length];
                    for (int i = 0; i < arr.length; i++) bytes[i] = (Byte) arr[i];
                    ByteBuffer bb = ByteBuffer.wrap(bytes);
                    return new UUID(bb.getLong(), bb.getLong()).toString();
                }
            }
        } catch (Exception ignored) {
        }
        return dbValue.toString();
    }

    private String dbBytesToString(Object dbValue) {
        if (dbValue == null) return null;
        try {
            if (dbValue instanceof String) return (String) dbValue;
            if (dbValue instanceof byte[]) {
                return new String((byte[]) dbValue, StandardCharsets.UTF_8);
            }
            if (dbValue instanceof Blob) {
                Blob blob = (Blob) dbValue;
                try (InputStream is = blob.getBinaryStream(); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                    byte[] buffer = new byte[4096];
                    int read;
                    while ((read = is.read(buffer)) != -1) baos.write(buffer, 0, read);
                    return new String(baos.toByteArray(), StandardCharsets.UTF_8);
                }
            }
            if (dbValue instanceof Clob) {
                Clob clob = (Clob) dbValue;
                try (Reader reader = clob.getCharacterStream()) {
                    StringBuilder sb = new StringBuilder();
                    char[] buf = new char[4096];
                    int r;
                    while ((r = reader.read(buf)) != -1) sb.append(buf, 0, r);
                    return sb.toString();
                }
            }
        } catch (Exception ignored) {
        }
        return dbValue.toString();
    }

}
