package com.codej.repository;

import com.codej.model.TeacherSubjectAssignments;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ITeacherSubjectAssignmentsRepository extends IGenericRepository<TeacherSubjectAssignments, UUID> {

    @Query("SELECT t FROM TeacherSubjectAssignments t JOIN FETCH t.course c WHERE t.teacher.idTeacher = :teacherId AND t.degree.idDegree = :degreeId AND t.educationLevel.idEducationLevel = :educationLevelId")
    List<TeacherSubjectAssignments> findByTeacherAndDegreeAndEducationLevel(@Param("teacherId") UUID teacherId, @Param("degreeId") UUID degreeId, @Param("educationLevelId") UUID educationLevelId);

    @Query("SELECT t FROM TeacherSubjectAssignments t JOIN FETCH t.course c WHERE t.teacher.idTeacher = :teacherId")
    List<TeacherSubjectAssignments> findByTeacher(@Param("teacherId") UUID teacherId);

}
