package com.codej.mapper;

import com.codej.dto.TeacherSubjectAssignmentsDTO;
import com.codej.model.TeacherSubjectAssignments;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeacherSubjectAssignmentsMapper {

    @Mapping(target = "teacherId", source = "teacher.idTeacher")
    @Mapping(target = "educationLevelId", source = "educationLevel.idEducationLevel")
    @Mapping(target = "degreeId", source = "degree.idDegree")
    @Mapping(target = "courseId", source = "course.idCourse")
    TeacherSubjectAssignmentsDTO mapIn(TeacherSubjectAssignments teacherSubjectAssignments);

    @Mapping(target = "teacher.idTeacher", source = "teacherId")
    @Mapping(target = "educationLevel.idEducationLevel", source = "educationLevelId")
    @Mapping(target = "degree.idDegree", source = "degreeId")
    @Mapping(target = "course.idCourse", source = "courseId")
    TeacherSubjectAssignments mapOut(TeacherSubjectAssignmentsDTO teacherSubjectAssignmentsDTO);

    List<TeacherSubjectAssignmentsDTO> mapIn(List<TeacherSubjectAssignments> teacherSubjectAssignmentss);
}
