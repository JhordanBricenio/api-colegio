package com.codej.service.impl;


import com.codej.exceptions.ResourceNotFoundException;
import com.codej.model.Role;
import com.codej.model.Teacher;
import com.codej.repository.IGenericRepository;
import com.codej.repository.IRoleRepository;
import com.codej.repository.ITeacherRepository;
import com.codej.service.ITeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class TeacherServiceImpl extends CRUDGenericImpl<Teacher, UUID> implements ITeacherService {

    private final ITeacherRepository teacherRepository;
    private final IRoleRepository roleRepository;

    @Value("${apis.token}")
    private  String apiToken;


    @Override
    protected IGenericRepository<Teacher, UUID> getRepository() {
        return teacherRepository;
    }

    @Override
    public Teacher saveTeacher(Teacher teacher) throws Exception {
        Role role = roleRepository.findById(teacher.getUser().getRole().getIdRole())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el rol con id: " + teacher.getUser().getRole().getIdRole()));
        teacher.getUser().setRole(role);
        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher findByDni(String dni) throws Exception {
        return teacherRepository.findTeacherByDni(dni)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el docente con DNI: " + dni));
    }

    @Override
    public Page<Teacher> findAllPaged(Pageable pageable) throws Exception {
        return teacherRepository.findAll(pageable);
    }

}
