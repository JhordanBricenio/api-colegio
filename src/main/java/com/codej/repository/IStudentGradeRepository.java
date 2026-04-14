package com.codej.repository;

import com.codej.model.StudentGrade;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface IStudentGradeRepository extends IGenericRepository<StudentGrade, UUID> {

    @Query("SELECT sg FROM StudentGrade sg " +
            "LEFT JOIN FETCH sg.teacher t " +
            "LEFT JOIN FETCH t.user tu " +
            "WHERE sg.student.idStudent = :studentId")
    List<StudentGrade> findByStudentIdWithTeacher(@Param("studentId") UUID studentId);

        @Query("SELECT sg FROM StudentGrade sg " +
                "WHERE sg.student.idStudent = :studentId")
    List<StudentGrade> findByStudentId(@Param("studentId") UUID studentId);

}
