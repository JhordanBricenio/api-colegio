package com.codej.mapper;

import com.codej.dto.StudentDTO;
import com.codej.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(target = "user.role.idRole", source = "user.rolId")
    @Mapping(target = "degree.idDegree", source = "idDegree")
    @Mapping(target = "educationLevel.idEducationLevel", source = "idEducationLevel")
    Student toStudentEntity(StudentDTO studentDTO);


    @Mapping(target = "user.rolId", source = "user.role.idRole")
    @Mapping(target = "idDegree", source = "degree.idDegree")
    @Mapping(target = "idEducationLevel", source = "educationLevel.idEducationLevel")
    StudentDTO toStudentDTO(Student student);


    List<StudentDTO> toStudentDTOList(List<Student> students);
}
