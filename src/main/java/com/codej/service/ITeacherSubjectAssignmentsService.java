package com.codej.service;


import com.codej.model.TeacherSubjectAssignments;

import java.util.List;
import java.util.UUID;

public interface ITeacherSubjectAssignmentsService extends ICRUDService<TeacherSubjectAssignments, UUID> {

    List<TeacherSubjectAssignments> findByTeacher(UUID teacherId) throws Exception;

}
