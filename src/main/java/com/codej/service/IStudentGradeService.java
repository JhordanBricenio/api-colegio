package com.codej.service;


import com.codej.dto.CourseRecordDTO;
import com.codej.model.StudentGrade;

import java.util.List;
import java.util.UUID;

public interface IStudentGradeService extends ICRUDService<StudentGrade, UUID> {

    List<StudentGrade> findGradesByStudentId(UUID studentId) throws Exception;

    List<CourseRecordDTO> getCourseRecordsByStudentId(UUID studentId) throws Exception;

}
