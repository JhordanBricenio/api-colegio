package com.codej.service.impl;

import com.codej.exceptions.ResourceNotFoundException;
import com.codej.model.Degree;
import com.codej.model.EducationLevel;
import com.codej.model.Parent;
import com.codej.model.Registration;
import com.codej.model.Student;
import com.codej.repository.IDegreeRepository;
import com.codej.repository.IEducationLevelRepository;
import com.codej.repository.IGenericRepository;
import com.codej.repository.IParentRepository;
import com.codej.repository.IRegistrationRepository;
import com.codej.repository.IStudentRepository;
import com.codej.service.IRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl extends CRUDGenericImpl<Registration, UUID> implements IRegistrationService {

    private final IRegistrationRepository registrationRepository;
    private final IParentRepository parentRepository;
    private final IStudentRepository studentRepository;
    private final IDegreeRepository degreeRepository;
    private final IEducationLevelRepository educationLevelRepository;

    @Override
    protected IGenericRepository<Registration, UUID> getRepository() {
        return registrationRepository;
    }

    @Override
    public Registration saveRegistration(Registration registration) throws Exception {
        Parent parent = parentRepository.findById(registration.getParent().getIdParent())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el apoderado con id: " + registration.getParent().getIdParent()));
        registration.setParent(parent);

        Student student = studentRepository.findById(registration.getStudent().getIdStudent())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el estudiante con id: " + registration.getStudent().getIdStudent()));
        registration.setStudent(student);

        Degree degree = degreeRepository.findById(registration.getDegree().getIdDegree())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el grado con id: " + registration.getDegree().getIdDegree()));
        registration.setDegree(degree);

        EducationLevel educationLevel = educationLevelRepository.findById(registration.getEducationLevel().getIdEducationLevel())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el nivel educativo con id: " + registration.getEducationLevel().getIdEducationLevel()));
        registration.setEducationLevel(educationLevel);

        return registrationRepository.save(registration);
    }

    @Override
    public Registration updateRegistration(Registration registration, UUID id) throws Exception {
        registrationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la matrícula con id: " + id));
        registration.setIdRegistration(id);
        return saveRegistration(registration);
    }

    @Override
    public Page<Registration> findAllPaged(Pageable pageable) throws Exception {
        return registrationRepository.findAll(pageable);
    }

}
