package com.codej.service.impl;

import com.codej.exceptions.ResourceNotFoundException;
import com.codej.model.Parent;
import com.codej.model.Registration;
import com.codej.model.Student;
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
    private final IStudentRepository studentRepository;
    private final IParentRepository parentRepository;

    @Override
    protected IGenericRepository<Registration, UUID> getRepository() {
        return registrationRepository;
    }

    @Override
    public Registration saveRegistration(Registration registration, UUID idStudent, UUID idParent) throws Exception {
        Student student = studentRepository.findById(idStudent)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el estudiante con id: " + idStudent));
        Parent parent = parentRepository.findById(idParent)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el apoderado con id: " + idParent));
        if (registration.getIdRegistration() != null) {
            registrationRepository.findById(registration.getIdRegistration())
                    .orElseThrow(() -> new ResourceNotFoundException("No se encontró la matrícula con id: " + registration.getIdRegistration()));
        }
        registration.setStudent(student);
        registration.setParent(parent);
        return registrationRepository.save(registration);
    }

    @Override
    public Page<Registration> findAllPaged(Pageable pageable) throws Exception {
        return registrationRepository.findAll(pageable);
    }
}
