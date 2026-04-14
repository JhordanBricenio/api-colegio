package com.codej.service;

import com.codej.dto.attendance.AttendanceStudentDTO;
import com.codej.dto.attendance.BulkAttendanceStudentRequestDTO;
import com.codej.dto.attendance.BulkAttendanceResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IAttendanceStudentService {
    AttendanceStudentDTO save(AttendanceStudentDTO dto) throws Exception;
    AttendanceStudentDTO findById(UUID id) throws Exception;
    AttendanceStudentDTO update(UUID id, AttendanceStudentDTO dto) throws Exception;
    Page<AttendanceStudentDTO> findPaged(Pageable pageable, String studentId, String startDate, String endDate, String status) throws Exception;
    BulkAttendanceResponseDTO saveBulk(BulkAttendanceStudentRequestDTO bulkRequest) throws Exception;
}
