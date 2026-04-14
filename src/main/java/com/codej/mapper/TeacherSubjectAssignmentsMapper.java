package com.codej.mapper;

import com.codej.dto.TeacherSubjectAssignmentsDTO;
import com.codej.model.Course;
import com.codej.model.Degree;
import com.codej.model.EducationLevel;
import com.codej.model.Teacher;
import com.codej.model.TeacherSubjectAssignments;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface TeacherSubjectAssignmentsMapper {

    @Mapping(target = "teacherId", source = "teacher.idTeacher")
    @Mapping(target = "educationLevelId", source = "educationLevel.idEducationLevel")
    @Mapping(target = "degreeId", source = "degree.idDegree")
    @Mapping(target = "courseId", source = "course.idCourse")
    TeacherSubjectAssignmentsDTO mapIn(TeacherSubjectAssignments teacherSubjectAssignments);

    // Mapear desde ids directamente a las entidades usando helpers que devuelven null cuando id es null
    @Mapping(target = "teacher", source = "teacherId")
    @Mapping(target = "educationLevel", source = "educationLevelId")
    @Mapping(target = "degree", source = "degreeId")
    @Mapping(target = "course", source = "courseId")
    TeacherSubjectAssignments mapOut(TeacherSubjectAssignmentsDTO teacherSubjectAssignmentsDTO);

    List<TeacherSubjectAssignmentsDTO> mapIn(List<TeacherSubjectAssignments> teacherSubjectAssignmentss);

    // Helpers para MapStruct: convierten String <-> entidades con UUID
    default Teacher mapToTeacher(String id) {
        if (id == null) return null;
        String trimmed = id.trim();
        if (trimmed.isEmpty()) return null;
        try {
            Teacher t = new Teacher();
            t.setIdTeacher(UUID.fromString(trimmed));
            return t;
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    default String mapFromTeacher(Teacher teacher) {
        if (teacher == null || teacher.getIdTeacher() == null) return null;
        return teacher.getIdTeacher().toString();
    }

    default Degree mapToDegree(String id) {
        if (id == null) return null;
        String trimmed = id.trim();
        if (trimmed.isEmpty()) return null;
        try {
            Degree d = new Degree();
            d.setIdDegree(UUID.fromString(trimmed));
            return d;
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    default String mapFromDegree(Degree degree) {
        if (degree == null || degree.getIdDegree() == null) return null;
        return degree.getIdDegree().toString();
    }

    default EducationLevel mapToEducationLevel(String id) {
        if (id == null) return null;
        String trimmed = id.trim();
        if (trimmed.isEmpty()) return null;
        try {
            EducationLevel e = new EducationLevel();
            e.setIdEducationLevel(UUID.fromString(trimmed));
            return e;
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    default String mapFromEducationLevel(EducationLevel educationLevel) {
        if (educationLevel == null || educationLevel.getIdEducationLevel() == null) return null;
        return educationLevel.getIdEducationLevel().toString();
    }

    // Course helpers
    default Course mapToCourse(String id) {
        if (id == null) return null;
        String trimmed = id.trim();
        if (trimmed.isEmpty()) return null;
        try {
            Course c = new Course();
            c.setIdCourse(UUID.fromString(trimmed));
            return c;
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    default String mapFromCourse(Course course) {
        if (course == null || course.getIdCourse() == null) return null;
        return course.getIdCourse().toString();
    }

}
