package com.codej.service.impl;

import com.codej.dto.attendance.AttendanceTeacherDTO;
import com.codej.dto.attendance.BulkAttendanceRequestDTO;
import com.codej.dto.attendance.BulkAttendanceResponseDTO;
import com.codej.emuns.StatusAttendance;
import com.codej.exceptions.DuplicateResourceException;
import com.codej.mapper.AttendanceTeacherMapper;
import com.codej.model.AttendanceTeacher;
import com.codej.repository.IAttendanceTeacherRepository;
import com.codej.repository.ITeacherRepository;
import com.codej.service.IAttendanceTeacherService;
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
public class AttendanceTeacherServiceImpl implements IAttendanceTeacherService {

    private final IAttendanceTeacherRepository repository;
    private final ITeacherRepository teacherRepository;
    private final AttendanceTeacherMapper mapper;

    @Override
    public AttendanceTeacherDTO save(AttendanceTeacherDTO dto) throws Exception {
        if (dto.getTeacherId() == null) throw new IllegalArgumentException("teacherId required");
        LocalDate date;
        try {
            date = LocalDate.parse(dto.getAttendanceDate());
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("attendanceDate invalid, expected yyyy-MM-dd");
        }

        var teacher = teacherRepository.findById(dto.getTeacherId()).orElseThrow(() -> new IllegalArgumentException("Teacher not found"));

        Optional<AttendanceTeacher> existing = repository.findByTeacher_IdTeacherAndAttendanceDateAndSession(teacher.getIdTeacher(), date, dto.getSession());
        if (existing.isPresent()) {
            throw new DuplicateResourceException("AttendanceTeacher", "teacherId+attendanceDate+session",
                    String.format("%s+%s+%s", teacher.getIdTeacher(), date, dto.getSession()));
        }

        AttendanceTeacher entity = new AttendanceTeacher();
        entity.setTeacher(teacher);
        entity.setAttendanceDate(date);
        entity.setSession(dto.getSession());
        if (dto.getStatus() != null) entity.setStatus(StatusAttendance.valueOf(dto.getStatus().name()));
        entity.setMinutesLate(dto.getMinutesLate());
        entity.setJustification(dto.getJustification());
        entity.setSource(dto.getSource());
        repository.save(entity);
        return mapper.toDTO(entity);
    }

    @Override
    public AttendanceTeacherDTO findById(UUID id) throws Exception {
        return repository.findById(id).map(mapper::toDTO).orElseThrow(() -> new NoSuchElementException("Attendance not found"));

    }

    @Override
    public AttendanceTeacherDTO update(UUID id, AttendanceTeacherDTO dto) throws Exception {
        AttendanceTeacher entity = repository.findById(id).orElseThrow(() -> new NoSuchElementException("Attendance not found"));
        if (StringUtils.hasText(dto.getStatus().name())) entity.setStatus(StatusAttendance.valueOf(dto.getStatus().name()));
        if (dto.getMinutesLate() != null) entity.setMinutesLate(dto.getMinutesLate());
        if (StringUtils.hasText(dto.getJustification())) entity.setJustification(dto.getJustification());
        if (StringUtils.hasText(dto.getSource())) entity.setSource(dto.getSource());
        entity.setUpdatedAt(java.time.LocalDateTime.now());
        repository.save(entity);
        return mapper.toDTO(entity);
    }

    @Override
    public Page<AttendanceTeacherDTO> findPaged(Pageable pageable, String teacherId, String startDate, String endDate, String status) throws Exception {
        Pageable p = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("attendanceDate").descending());
        Page<AttendanceTeacher> page;
        if (StringUtils.hasText(teacherId)) {
            UUID tId = UUID.fromString(teacherId);
            page = repository.findAllByTeacher_IdTeacher(tId, p);
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
    public BulkAttendanceResponseDTO saveBulk(BulkAttendanceRequestDTO bulkRequest) throws Exception {
        LocalDate date = LocalDate.parse(bulkRequest.getDate());
        String session = bulkRequest.getSession();
        String strategy = bulkRequest.getConflictStrategy() == null ? "skip" : bulkRequest.getConflictStrategy();

        List<BulkAttendanceRequestDTO.BulkRecord> records = bulkRequest.getRecords();
        if (records == null || records.isEmpty()) return new BulkAttendanceResponseDTO(0, 0, 0, Collections.emptyList());
        List<String> errors = new ArrayList<>();
        int created = 0, updated = 0, skipped = 0;

        for (BulkAttendanceRequestDTO.BulkRecord rec : records) {
            UUID tId;
            try {
                tId = UUID.fromString(rec.getTeacherId());
            } catch (Exception ex) {
                errors.add("Invalid teacherId: " + rec.getTeacherId());
                continue;
            }
            Optional<com.codej.model.Teacher> tOpt = teacherRepository.findById(tId);
            if (tOpt.isEmpty()) {
                errors.add("Teacher not found: " + rec.getTeacherId());
                continue;
            }
            com.codej.model.Teacher teacher = tOpt.get();
            Optional<AttendanceTeacher> existingOpt = repository.findByTeacher_IdTeacherAndAttendanceDateAndSession(teacher.getIdTeacher(), date, session);
            if (existingOpt.isPresent()) {
                if ("fail".equalsIgnoreCase(strategy)) {
                    throw new DuplicateResourceException("AttendanceTeacher", "teacherId+attendanceDate+session",
                            String.format("%s+%s+%s", teacher.getIdTeacher(), date, session));
                } else if ("update".equalsIgnoreCase(strategy)) {
                    AttendanceTeacher e = existingOpt.get();
                    if (rec.getStatus() != null) e.setStatus(StatusAttendance.valueOf(rec.getStatus().name()));
                    e.setMinutesLate(rec.getMinutesLate());
                    e.setJustification(rec.getJustification());
                    e.setSource(rec.getSource());
                    e.setUpdatedAt(java.time.LocalDateTime.now());
                    repository.save(e);
                    updated++;
                } else { // skip
                    skipped++;
                }
            } else {
                AttendanceTeacher e = new AttendanceTeacher();
                e.setTeacher(teacher);
                e.setAttendanceDate(date);
                e.setSession(session);
                if (rec.getStatus() != null) e.setStatus(StatusAttendance.valueOf(rec.getStatus().name()));
                e.setMinutesLate(rec.getMinutesLate());
                e.setJustification(rec.getJustification());
                e.setSource(rec.getSource());
                repository.save(e);
                created++;
            }
        }

        return new BulkAttendanceResponseDTO(created, updated, skipped, errors);
    }
}
