package com.codej.service.impl;


import com.codej.exceptions.ResourceNotFoundException;
import com.codej.model.Role;
import com.codej.model.Student;
import com.codej.repository.IGenericRepository;
import com.codej.repository.IRoleRepository;
import com.codej.repository.IStudentRepository;
import com.codej.service.IStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class StudentServiceImpl extends CRUDGenericImpl<Student, UUID> implements IStudentService {

    private final IStudentRepository studentRepository;
    private final IRoleRepository roleRepository;
    private final com.codej.repository.IDegreeRepository degreeRepository;
    private final com.codej.repository.IEducationLevelRepository educationLevelRepository;

    @Value("${apis.token}")
    private  String apiToken;


    @Override
    protected IGenericRepository<Student, UUID> getRepository() {
        return studentRepository;
    }

    @Override
    public Student saveStudent(Student student) throws Exception {
        Role role = roleRepository.findById(student.getUser().getRole().getIdRole())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el rol con id: " + student.getUser().getRole().getIdRole()));
        student.getUser().setRole(role);

        // Attach Degree if present
        if (student.getDegree() != null && student.getDegree().getIdDegree() != null) {
            UUID degreeId = student.getDegree().getIdDegree();
            var degree = degreeRepository.findById(degreeId)
                    .orElseThrow(() -> new ResourceNotFoundException("No se encontró el grado con id: " + degreeId));
            student.setDegree(degree);
        }

        // Attach EducationLevel if present
        if (student.getEducationLevel() != null && student.getEducationLevel().getIdEducationLevel() != null) {
            UUID eduId = student.getEducationLevel().getIdEducationLevel();
            var edu = educationLevelRepository.findById(eduId)
                    .orElseThrow(() -> new ResourceNotFoundException("No se encontró el nivel educativo con id: " + eduId));
            student.setEducationLevel(edu);
        }
        return studentRepository.save(student);
    }

    @Override
    public Student update(Student student, UUID id) throws Exception {
        // Ensure target exists (CRUDGenericImpl.update already checks), but attach nested entities before calling super
        // Attach Role if present
        if (student.getUser() != null && student.getUser().getRole() != null && student.getUser().getRole().getIdRole() != null) {
            Role role = roleRepository.findById(student.getUser().getRole().getIdRole())
                    .orElseThrow(() -> new ResourceNotFoundException("No se encontró el rol con id: " + student.getUser().getRole().getIdRole()));
            student.getUser().setRole(role);
        }

        // Attach Degree if present
        if (student.getDegree() != null && student.getDegree().getIdDegree() != null) {
            UUID degreeId = student.getDegree().getIdDegree();
            var degree = degreeRepository.findById(degreeId)
                    .orElseThrow(() -> new ResourceNotFoundException("No se encontró el grado con id: " + degreeId));
            student.setDegree(degree);
        }

        // Attach EducationLevel if present
        if (student.getEducationLevel() != null && student.getEducationLevel().getIdEducationLevel() != null) {
            UUID eduId = student.getEducationLevel().getIdEducationLevel();
            var edu = educationLevelRepository.findById(eduId)
                    .orElseThrow(() -> new ResourceNotFoundException("No se encontró el nivel educativo con id: " + eduId));
            student.setEducationLevel(edu);
        }

        return super.update(student, id);
    }

    @Override
    public Student findByDni(String dni) throws Exception {
        return studentRepository.findStudentByDni(dni)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el docente con DNI: " + dni));
    }

    @Override
    public Page<Student> findAllPaged(Pageable pageable) throws Exception {
        return studentRepository.findAll(pageable);
    }

}
