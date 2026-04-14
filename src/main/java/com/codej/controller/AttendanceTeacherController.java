package com.codej.controller;

import com.codej.dto.attendance.AttendanceTeacherDTO;
import com.codej.dto.attendance.BulkAttendanceRequestDTO;
import com.codej.dto.attendance.BulkAttendanceResponseDTO;
import com.codej.service.IAttendanceTeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.codej.constants.ApiConstants.TEACHER_ATTENDANCES;

@RestController
@RequestMapping(TEACHER_ATTENDANCES)
@RequiredArgsConstructor
public class AttendanceTeacherController {

    private final IAttendanceTeacherService service;

    @PostMapping("/{teacherId}")
    public ResponseEntity<AttendanceTeacherDTO> createIndividual(@PathVariable UUID teacherId, @Valid @RequestBody AttendanceTeacherDTO dto) throws Exception {
        dto.setTeacherId(teacherId);
        AttendanceTeacherDTO saved = service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PostMapping("/bulk")
    public ResponseEntity<BulkAttendanceResponseDTO> createBulk(@RequestBody BulkAttendanceRequestDTO bulk) throws Exception {
        BulkAttendanceResponseDTO resp = service.saveBulk(bulk);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttendanceTeacherDTO> getById(@PathVariable UUID id) throws Exception {
        AttendanceTeacherDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AttendanceTeacherDTO> patch(@PathVariable UUID id, @RequestBody AttendanceTeacherDTO dto) throws Exception {
        AttendanceTeacherDTO updated = service.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/paged/{page}")
    public Page<AttendanceTeacherDTO> paged(@PathVariable int page,
                                            @RequestParam(required = false) String teacherId,
                                            @RequestParam(required = false) String startDate,
                                            @RequestParam(required = false) String endDate,
                                            @RequestParam(required = false) String status) throws Exception {

        return service.findPaged(PageRequest.of(page, 8), teacherId, startDate, endDate, status);
    }

    @GetMapping("/by-teacher/{teacherId}")
    public ResponseEntity<Page<AttendanceTeacherDTO>> byTeacher(@PathVariable UUID teacherId, @RequestParam int page) throws Exception {
        Page<AttendanceTeacherDTO> p = service.findPaged(PageRequest.of(page, 8), teacherId.toString(), null, null, null);
        return ResponseEntity.ok(p);
    }
}
