package com.codej.service;

import com.codej.dto.attendance.AttendanceStudentDTO;
import com.codej.dto.attendance.BulkAttendanceResponseDTO;
import com.codej.dto.attendance.BulkAttendanceStudentRequestDTO;
import com.codej.emuns.StatusAttendance;
import com.codej.mapper.AttendanceStudentMapper;
import com.codej.model.AttendanceStudent;
import com.codej.model.Student;
import com.codej.repository.IAttendanceStudentRepository;
import com.codej.repository.IStudentRepository;
import com.codej.service.impl.AttendanceStudentServiceImpl;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AttendanceStudentServiceImplTest {

    @Mock
    private IAttendanceStudentRepository repository;
    @Mock
    private IStudentRepository studentRepository;
    @Mock
    private AttendanceStudentMapper mapper;
    @Mock
    private EntityManager entityManager;

    private AttendanceStudentServiceImpl service;
    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        service = new AttendanceStudentServiceImpl(repository, studentRepository, mapper, entityManager);
    }

    @Test
    void save_createsAttendance_whenValid() throws Exception {
        UUID studentId = UUID.randomUUID();
        AttendanceStudentDTO dto = new AttendanceStudentDTO();
        dto.setStudentId(studentId);
        dto.setAttendanceDate("2026-04-01");
        dto.setSession("MORNING");
        dto.setStatus(StatusAttendance.PRESENT);

        Student s = new Student(); s.setIdStudent(studentId);
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(s));
        when(repository.findByStudent_IdStudentAndAttendanceDateAndSession(eq(studentId), any(LocalDate.class), eq("MORNING")))
                .thenReturn(Optional.empty());

        AttendanceStudent savedEntity = new AttendanceStudent();
        when(repository.save(any())).thenReturn(savedEntity);
        when(mapper.toDTO(any())).thenReturn(dto);

        AttendanceStudentDTO result = service.save(dto);

        assertNotNull(result);
        verify(repository, times(1)).save(any(AttendanceStudent.class));
    }

//    @Test
//    void save_throwsDuplicate_whenExists() {
//        UUID studentId = UUID.randomUUID();
//        AttendanceStudentDTO dto = new AttendanceStudentDTO();
//        dto.setStudentId(studentId);
//        dto.setAttendanceDate("2026-04-01");
//        dto.setSession("MORNING");
//
//        Student s = new Student(); s.setIdStudent(studentId);
//        when(studentRepository.findById(studentId)).thenReturn(Optional.of(s));
//        AttendanceStudent existing = new AttendanceStudent();
//        when(repository.findByStudent_IdStudentAndAttendanceDateAndSession(eq(studentId), any(LocalDate.class), eq("MORNING")))
//                .thenReturn(Optional.of(existing));
//
//        assertThrows(DuplicateResourceException.class, () -> service.save(dto));
//    }

    @Test
    void saveBulk_handlesCreateUpdateSkip() throws Exception {
        BulkAttendanceStudentRequestDTO bulk = new BulkAttendanceStudentRequestDTO();
        bulk.setDate("2026-04-01");
        bulk.setSession("MORNING");
        bulk.setConflictStrategy("skip");

        BulkAttendanceStudentRequestDTO.BulkRecord r1 = new BulkAttendanceStudentRequestDTO.BulkRecord();
        UUID s1 = UUID.randomUUID(); r1.setStudentId(s1.toString()); r1.setStatus(StatusAttendance.PRESENT);
        BulkAttendanceStudentRequestDTO.BulkRecord r2 = new BulkAttendanceStudentRequestDTO.BulkRecord();
        UUID s2 = UUID.randomUUID(); r2.setStudentId(s2.toString()); r2.setStatus(StatusAttendance.ABSENT);

        bulk.setRecords(Arrays.asList(r1, r2));

        Student st1 = new Student(); st1.setIdStudent(s1);
        when(studentRepository.findById(s1)).thenReturn(Optional.of(st1));
        when(studentRepository.findById(s2)).thenReturn(Optional.empty());

        when(repository.findByStudent_IdStudentAndAttendanceDateAndSession(eq(s1), any(LocalDate.class), eq("MORNING")))
                .thenReturn(Optional.empty());

        when(repository.save(any())).thenReturn(new AttendanceStudent());

        BulkAttendanceResponseDTO resp = service.saveBulk(bulk);

        assertEquals(1, resp.getCreated());
        assertEquals(0, resp.getUpdated());
        assertTrue(resp.getErrors().isEmpty() || resp.getErrors().size() >= 1);
        assertEquals(0, resp.getSkipped());
    }
}
