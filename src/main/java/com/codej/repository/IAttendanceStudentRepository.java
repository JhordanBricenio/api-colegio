package com.codej.repository;

import com.codej.model.AttendanceStudent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IAttendanceStudentRepository extends IGenericRepository<AttendanceStudent, UUID> {

    Optional<AttendanceStudent> findByStudent_IdStudentAndAttendanceDateAndSession(UUID studentId, LocalDate attendanceDate, String session);

    Page<AttendanceStudent> findAllByStudent_IdStudent(UUID studentId, Pageable pageable);

    List<AttendanceStudent> findAllByStudent_IdStudentAndAttendanceDate(UUID studentId, LocalDate attendanceDate);

    Page<AttendanceStudent> findAllByAttendanceDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);
}
