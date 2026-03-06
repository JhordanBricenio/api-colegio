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

import java.util.List;
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
    public Registration saveRegistration(Registration registration) throws Exception {
        if (registration.getStudent() == null || registration.getStudent().getIdStudent() == null) {
            throw new ResourceNotFoundException("El estudiante es requerido para crear una matrícula");
        }
        if (registration.getParent() == null || registration.getParent().getIdParent() == null) {
            throw new ResourceNotFoundException("El padre es requerido para crear una matrícula");
        }
        Student student = studentRepository.findById(registration.getStudent().getIdStudent())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró el estudiante con id: " + registration.getStudent().getIdStudent()));
        Parent parent = parentRepository.findById(registration.getParent().getIdParent())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró el padre con id: " + registration.getParent().getIdParent()));
        registration.setStudent(student);
        registration.setParent(parent);
        return registrationRepository.save(registration);
    }

    @Override
    public List<Registration> findAllWithDetails() throws Exception {
        return registrationRepository.findAllWithDetails();
    }

    @Override
    public Page<Registration> findAllWithDetailsPaged(Pageable pageable) throws Exception {
        return registrationRepository.findAllWithDetailsPaged(pageable);
    }
}
