package com.codej.service;

import com.codej.dto.attendance.AttendanceTeacherDTO;
import com.codej.dto.attendance.BulkAttendanceRequestDTO;
import com.codej.dto.attendance.BulkAttendanceResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IAttendanceTeacherService {
    AttendanceTeacherDTO save(AttendanceTeacherDTO dto) throws Exception;
    AttendanceTeacherDTO findById(UUID id) throws Exception;
    AttendanceTeacherDTO update(UUID id, AttendanceTeacherDTO dto) throws Exception;
    Page<AttendanceTeacherDTO> findPaged(Pageable pageable, String teacherId, String startDate, String endDate, String status) throws Exception;
    BulkAttendanceResponseDTO saveBulk(BulkAttendanceRequestDTO bulkRequest) throws Exception;
}
