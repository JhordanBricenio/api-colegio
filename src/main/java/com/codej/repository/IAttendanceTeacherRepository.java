package com.codej.repository;

import com.codej.model.AttendanceTeacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IAttendanceTeacherRepository extends IGenericRepository<AttendanceTeacher, UUID> {

    Optional<AttendanceTeacher> findByTeacher_IdTeacherAndAttendanceDateAndSession(UUID teacherId, LocalDate attendanceDate, String session);

    Page<AttendanceTeacher> findAllByTeacher_IdTeacher(UUID teacherId, Pageable pageable);

    List<AttendanceTeacher> findAllByTeacher_IdTeacherAndAttendanceDate(UUID teacherId, LocalDate attendanceDate);

    Page<AttendanceTeacher> findAllByAttendanceDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);
}
