package com.codej.mapper;

import com.codej.dto.attendance.AttendanceStudentDTO;
import com.codej.model.AttendanceStudent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface AttendanceStudentMapper {

    // Al convertir DTO -> entity dejamos student/course/degree en null y los setea el servicio
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "course", ignore = true)
    @Mapping(target = "degree", ignore = true)
    AttendanceStudent toEntity(AttendanceStudentDTO dto);

    // Entity -> DTO: mapear studentId, courseId y degreeId
    @Mapping(target = "studentId", source = "student.idStudent")
    @Mapping(target = "courseId", source = "course.idCourse")
    @Mapping(target = "degreeId", source = "degree.idDegree")
    @Mapping(target = "attendanceDate", source = "attendanceDate")
    @Mapping(target = "status", source = "status")
    AttendanceStudentDTO toDTO(AttendanceStudent entity);

    default LocalDate stringToLocalDate(String date) {
        if (date == null) return null;
        return LocalDate.parse(date);
    }

    default String localDateToString(LocalDate date) {
        if (date == null) return null;
        return date.toString();
    }
}
