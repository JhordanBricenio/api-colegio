package com.codej.service.impl;

import com.codej.dto.attendance.AttendanceStudentDTO;
import com.codej.dto.attendance.BulkAttendanceResponseDTO;
import com.codej.dto.attendance.BulkAttendanceStudentRequestDTO;
import com.codej.emuns.StatusAttendance;
import com.codej.exceptions.DuplicateResourceException;
import com.codej.mapper.AttendanceStudentMapper;
import com.codej.model.AttendanceStudent;
import com.codej.model.Course;
import com.codej.model.Degree;
import com.codej.model.Student;
import com.codej.repository.IAttendanceStudentRepository;
import com.codej.repository.IStudentRepository;
import com.codej.service.IAttendanceStudentService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AttendanceStudentServiceImpl implements IAttendanceStudentService {

    private final IAttendanceStudentRepository repository;
    private final IStudentRepository studentRepository;
    private final AttendanceStudentMapper mapper;
    private final EntityManager entityManager;

    @Override
    public AttendanceStudentDTO save(AttendanceStudentDTO dto) throws Exception {
        if (dto.getStudentId() == null) throw new IllegalArgumentException("studentId required");
        LocalDate date;
        try {
            date = LocalDate.parse(dto.getAttendanceDate());
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("attendanceDate invalid, expected yyyy-MM-dd");
        }

        var student = studentRepository.findById(dto.getStudentId()).orElseThrow(() -> new IllegalArgumentException("Student not found"));

        Optional<AttendanceStudent> existing = repository.findByStudent_IdStudentAndAttendanceDateAndSession(student.getIdStudent(), date, dto.getSession());
        if (existing.isPresent()) {
            // Actualizar el registro existente en lugar de lanzar excepción
            AttendanceStudent entity = existing.get();
            if (dto.getStatus() != null) entity.setStatus(StatusAttendance.valueOf(dto.getStatus().name()));
            if (dto.getMinutesLate() != null) entity.setMinutesLate(dto.getMinutesLate());
            if (StringUtils.hasText(dto.getJustification())) entity.setJustification(dto.getJustification());
            if (StringUtils.hasText(dto.getSource())) entity.setSource(dto.getSource());
            if (dto.getCourseId() != null) {
                Course c = entityManager.getReference(Course.class, dto.getCourseId());
                entity.setCourse(c);
            }
            if (dto.getDegreeId() != null) {
                Degree d = entityManager.getReference(Degree.class, dto.getDegreeId());
                entity.setDegree(d);
            }
            entity.setUpdatedAt(java.time.LocalDateTime.now());
            repository.save(entity);
            return mapper.toDTO(entity);
        }

        AttendanceStudent entity = new AttendanceStudent();
        entity.setStudent(student);
        entity.setAttendanceDate(date);
        entity.setSession(dto.getSession());
        if (dto.getStatus() != null) entity.setStatus(StatusAttendance.valueOf(dto.getStatus().name()));
        entity.setMinutesLate(dto.getMinutesLate());
        entity.setJustification(dto.getJustification());
        entity.setSource(dto.getSource());
        if (dto.getCourseId() != null) {
            Course c = entityManager.getReference(Course.class, dto.getCourseId());
            entity.setCourse(c);
        }
        if (dto.getDegreeId() != null) {
            Degree d = entityManager.getReference(Degree.class, dto.getDegreeId());
            entity.setDegree(d);
        }
        repository.save(entity);
        return mapper.toDTO(entity);
    }

    @Override
    public AttendanceStudentDTO findById(UUID id) throws Exception {
        return repository.findById(id).map(mapper::toDTO).orElseThrow(() -> new NoSuchElementException("Attendance not found"));
    }

    @Override
    public AttendanceStudentDTO update(UUID id, AttendanceStudentDTO dto) throws Exception {
        AttendanceStudent entity = repository.findById(id).orElseThrow(() -> new NoSuchElementException("Attendance not found"));
        if (dto.getStatus() != null && StringUtils.hasText(dto.getStatus().name())) entity.setStatus(StatusAttendance.valueOf(dto.getStatus().name()));
        if (dto.getMinutesLate() != null) entity.setMinutesLate(dto.getMinutesLate());
        if (StringUtils.hasText(dto.getJustification())) entity.setJustification(dto.getJustification());
        if (StringUtils.hasText(dto.getSource())) entity.setSource(dto.getSource());
        if (dto.getCourseId() != null) {
            Course c = entityManager.getReference(Course.class, dto.getCourseId());
            entity.setCourse(c);
        }
        if (dto.getDegreeId() != null) {
            Degree d = entityManager.getReference(Degree.class, dto.getDegreeId());
            entity.setDegree(d);
        }
        entity.setUpdatedAt(java.time.LocalDateTime.now());
        repository.save(entity);
        return mapper.toDTO(entity);
    }

    @Override
    public Page<AttendanceStudentDTO> findPaged(Pageable pageable, String studentId, String startDate, String endDate, String status) throws Exception {
        Pageable p = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("attendanceDate").descending());
        Page<AttendanceStudent> page;
        if (StringUtils.hasText(studentId)) {
            UUID sId = UUID.fromString(studentId);
            page = repository.findAllByStudent_IdStudent(sId, p);
        } else if (StringUtils.hasText(startDate) && StringUtils.hasText(endDate)) {
            LocalDate s = LocalDate.parse(startDate);
            LocalDate e = LocalDate.parse(endDate);
            page = repository.findAllByAttendanceDateBetween(s, e, p);
        } else {
            page = repository.findAll(p);
        }
        return page.map(mapper::toDTO);
    }

    @Override
    @Transactional
    public BulkAttendanceResponseDTO saveBulk(BulkAttendanceStudentRequestDTO bulkRequest) throws Exception {
        LocalDate date = LocalDate.parse(bulkRequest.getDate());
        String session = bulkRequest.getSession();
        String strategy = bulkRequest.getConflictStrategy() == null ? "skip" : bulkRequest.getConflictStrategy();

        List<BulkAttendanceStudentRequestDTO.BulkRecord> records = bulkRequest.getRecords();
        if (records == null || records.isEmpty()) return new BulkAttendanceResponseDTO(0, 0, 0, Collections.emptyList());
        List<String> errors = new ArrayList<>();
        int created = 0; int updated = 0; int skipped = 0;

        for (BulkAttendanceStudentRequestDTO.BulkRecord rec : records) {
            UUID sId;
            try {
                sId = UUID.fromString(rec.getStudentId());
            } catch (Exception ex) {
                errors.add("Invalid studentId: " + rec.getStudentId());
                continue;
            }
            Optional<Student> sOpt = studentRepository.findById(sId);
            if (sOpt.isEmpty()) {
                errors.add("Student not found: " + rec.getStudentId());
                continue;
            }
            Student student = sOpt.get();
            Optional<AttendanceStudent> existingOpt = repository.findByStudent_IdStudentAndAttendanceDateAndSession(student.getIdStudent(), date, session);
            if (existingOpt.isPresent()) {
                if ("fail".equalsIgnoreCase(strategy)) {
                    throw new DuplicateResourceException("AttendanceStudent", "studentId+attendanceDate+session",
                            String.format("%s+%s+%s", student.getIdStudent(), date, session));
                } else if ("update".equalsIgnoreCase(strategy)) {
                    AttendanceStudent e = existingOpt.get();
                    if (rec.getStatus() != null) e.setStatus(StatusAttendance.valueOf(rec.getStatus().name()));
                    e.setMinutesLate(rec.getMinutesLate());
                    e.setJustification(rec.getJustification());
                    e.setSource(rec.getSource());
                    if (bulkRequest.getCourseId() != null) {
                        Course c = entityManager.getReference(Course.class, bulkRequest.getCourseId()); e.setCourse(c);
                    }
                    if (bulkRequest.getDegreeId() != null) {
                        Degree d = entityManager.getReference(Degree.class, bulkRequest.getDegreeId()); e.setDegree(d);
                    }
                    e.setUpdatedAt(java.time.LocalDateTime.now());
                    repository.save(e);
                    updated++;
                } else { // skip
                    skipped++;
                }
            } else {
                AttendanceStudent e = new AttendanceStudent();
                e.setStudent(student);
                e.setAttendanceDate(date);
                e.setSession(session);
                if (rec.getStatus() != null) e.setStatus(StatusAttendance.valueOf(rec.getStatus().name()));
                e.setMinutesLate(rec.getMinutesLate());
                e.setJustification(rec.getJustification());
                e.setSource(rec.getSource());
                if (bulkRequest.getCourseId() != null) { Course c = entityManager.getReference(Course.class, bulkRequest.getCourseId()); e.setCourse(c); }
                if (bulkRequest.getDegreeId() != null) { Degree d = entityManager.getReference(Degree.class, bulkRequest.getDegreeId()); e.setDegree(d); }
                repository.save(e);
                created++;
            }
        }

        return new BulkAttendanceResponseDTO(created, updated, skipped, errors);
    }
}
