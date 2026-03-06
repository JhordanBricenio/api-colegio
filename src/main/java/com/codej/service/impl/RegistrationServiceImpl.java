package com.codej.service.impl;

import com.codej.dto.RegistrationSummaryDTO;
import com.codej.exceptions.ResourceNotFoundException;
import com.codej.model.Registration;
import com.codej.repository.IGenericRepository;
import com.codej.repository.IRegistrationRepository;
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

    @Override
    protected IGenericRepository<Registration, UUID> getRepository() {
        return registrationRepository;
    }

    @Override
    public Registration saveRegistration(Registration registration) throws Exception {
        return registrationRepository.save(registration);
    }

    @Override
    public List<RegistrationSummaryDTO> findAllSummary() throws Exception {
        return registrationRepository.findAllSummary();
    }

    @Override
    public List<Registration> findAllWithDetails() throws Exception {
        return registrationRepository.findAllWithDetails();
    }

    @Override
    public Registration findByIdWithDetails(UUID id) throws Exception {
        return registrationRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la matrícula con id: " + id));
    }

    @Override
    public Page<Registration> findAllPaged(Pageable pageable) throws Exception {
        return registrationRepository.findAll(pageable);
    }
}
