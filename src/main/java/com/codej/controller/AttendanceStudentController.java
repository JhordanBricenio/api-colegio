package com.codej.controller;

import com.codej.dto.attendance.AttendanceStudentDTO;
import com.codej.dto.attendance.BulkAttendanceResponseDTO;
import com.codej.dto.attendance.BulkAttendanceStudentRequestDTO;
import com.codej.service.IAttendanceStudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.codej.constants.ApiConstants.*;

@RestController
@RequestMapping(STUDENT_BASE + "/attendances")
@RequiredArgsConstructor
public class AttendanceStudentController {

    private final IAttendanceStudentService service;

    @PostMapping("/{studentId}")
    public ResponseEntity<AttendanceStudentDTO> createIndividual(@PathVariable UUID studentId, @Valid @RequestBody AttendanceStudentDTO dto) throws Exception {
        dto.setStudentId(studentId);
        AttendanceStudentDTO saved = service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PostMapping("/bulk")
    public ResponseEntity<BulkAttendanceResponseDTO> createBulk(@RequestBody BulkAttendanceStudentRequestDTO bulk) throws Exception {
        BulkAttendanceResponseDTO resp = service.saveBulk(bulk);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttendanceStudentDTO> getById(@PathVariable UUID id) throws Exception {
        AttendanceStudentDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AttendanceStudentDTO> patch(@PathVariable UUID id, @RequestBody AttendanceStudentDTO dto) throws Exception {
        AttendanceStudentDTO updated = service.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/paged/{page}")
    public Page<AttendanceStudentDTO> paged(@PathVariable int page,
                                            @RequestParam(required = false) String studentId,
                                            @RequestParam(required = false) String startDate,
                                            @RequestParam(required = false) String endDate,
                                            @RequestParam(required = false) String status) throws Exception {

        return service.findPaged(PageRequest.of(page, 8), studentId, startDate, endDate, status);
    }

    @GetMapping("/by-student/{studentId}")
    public ResponseEntity<Page<AttendanceStudentDTO>> byStudent(@PathVariable UUID studentId, @RequestParam int page) throws Exception {
        Page<AttendanceStudentDTO> p = service.findPaged(PageRequest.of(page, 8), studentId.toString(), null, null, null);
        return ResponseEntity.ok(p);
    }
}
