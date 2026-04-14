package com.codej.mapper;

import com.codej.dto.StudentGradeDTO;
import com.codej.model.StudentGrade;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentGradeMapper {
    
    @Mapping(target = "teacherId", source = "teacher.idTeacher")
    @Mapping(target = "studentId", source = "student.idStudent")
    @Mapping(target = "courseId", source = "course.idCourse")
    StudentGradeDTO mapIn(StudentGrade studentGrade);


    @Mapping(target = "teacher.idTeacher", source = "teacherId")
    @Mapping(target = "student.idStudent", source = "studentId")
    @Mapping(target = "course.idCourse", source = "courseId")
    StudentGrade mapOut(StudentGradeDTO studentGradeDTO);

    List<StudentGradeDTO> mapIn(List<StudentGrade> studentGrades);
}
