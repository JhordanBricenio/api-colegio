package com.codej.mapper;

import com.codej.dto.attendance.AttendanceTeacherDTO;
import com.codej.model.AttendanceTeacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface AttendanceTeacherMapper {

    @Mapping(target = "teacher", ignore = true)
    AttendanceTeacher toEntity(AttendanceTeacherDTO dto);

    @Mapping(target = "teacherId", source = "teacher.idTeacher")
    @Mapping(target = "attendanceDate", source = "attendanceDate")
    @Mapping(target = "status", source = "status")
    AttendanceTeacherDTO toDTO(AttendanceTeacher entity);

    default LocalDate stringToLocalDate(String date) {
        if (date == null) return null;
        return LocalDate.parse(date);
    }

    default String localDateToString(LocalDate date) {
        if (date == null) return null;
        return date.toString();
    }
}
