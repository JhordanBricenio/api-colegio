package com.codej.service;

import com.codej.dto.RegistrationSummaryDTO;
import com.codej.model.Registration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IRegistrationService extends ICRUDService<Registration, UUID> {

    Registration saveRegistration(Registration registration) throws Exception;

    List<RegistrationSummaryDTO> findAllSummary() throws Exception;

    List<Registration> findAllWithDetails() throws Exception;

    Registration findByIdWithDetails(UUID id) throws Exception;

    Page<Registration> findAllPaged(Pageable pageable) throws Exception;
}
