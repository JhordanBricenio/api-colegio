package com.codej.mapper;

import com.codej.dto.TeacherDTO;
import com.codej.model.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
    // TeacherDTO to Teacher
    @Mapping(target = "user.role.idRole", source = "user.rolId")
    Teacher toTeacherEntity(TeacherDTO teacherDTO);

    // Teacher to TeacherDTO
    @Mapping(target = "user.rolId", source = "user.role.idRole")
    TeacherDTO toTeacherDTO(Teacher teacher);


    List<TeacherDTO> toTeacherDTOList(List<Teacher> teachers);
}
