package com.codej.service.impl;


import com.codej.model.TeacherSubjectAssignments;
import com.codej.repository.IGenericRepository;
import com.codej.repository.ITeacherSubjectAssignmentsRepository;
import com.codej.service.ITeacherSubjectAssignmentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class TeacherSubjectAssignmentsServiceImpl extends CRUDGenericImpl<TeacherSubjectAssignments, UUID> implements ITeacherSubjectAssignmentsService {

    private final ITeacherSubjectAssignmentsRepository teacherSubjectAssignmentsRepository;;

    @Override
    protected IGenericRepository<TeacherSubjectAssignments, UUID> getRepository() {
        return teacherSubjectAssignmentsRepository;
    }


}
