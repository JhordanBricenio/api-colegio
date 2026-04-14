package com.codej.mapper;

import com.codej.dto.attendance.AttendanceStudentDTO;
import com.codej.emuns.StatusAttendance;
import com.codej.model.AttendanceStudent;
import com.codej.model.Course;
import com.codej.model.Degree;
import com.codej.model.Student;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AttendanceStudentMapperTest {

    private final AttendanceStudentMapper mapper = Mappers.getMapper(AttendanceStudentMapper.class);

    @Test
    void toDTO_mapsEntityToDto_correctly() {
        AttendanceStudent entity = new AttendanceStudent();
        UUID attendanceId = UUID.randomUUID();
        UUID studentId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        UUID degreeId = UUID.randomUUID();

        entity.setIdAttendance(attendanceId);

        Student s = new Student();
        s.setIdStudent(studentId);
        Course c = new Course();
        c.setIdCourse(courseId);
        c.setName("Matemáticas");

        Degree d = new Degree();
        d.setIdDegree(degreeId);
        d.setCourse("Primaria 1");

        entity.setStudent(s);
        entity.setCourse(c);
        entity.setDegree(d);
        entity.setAttendanceDate(LocalDate.of(2026,4,1));
        entity.setStatus(StatusAttendance.PRESENT);
        entity.setMinutesLate(5);
        entity.setJustification("Llegó tarde por tráfico");
        entity.setSource("web");
        entity.setCreatedAt(LocalDateTime.now());

        AttendanceStudentDTO dto = mapper.toDTO(entity);

        assertNotNull(dto);
        assertEquals(attendanceId, dto.getIdAttendance());
        assertEquals(studentId, dto.getStudentId());
        assertEquals(courseId.toString(), dto.getCourseId());
        assertEquals(degreeId.toString(), dto.getDegreeId());
        assertEquals("2026-04-01", dto.getAttendanceDate());
        assertEquals(StatusAttendance.PRESENT, dto.getStatus());
        assertEquals(5, dto.getMinutesLate());
        assertEquals("Llegó tarde por tráfico", dto.getJustification());
        assertEquals("web", dto.getSource());
    }
}
