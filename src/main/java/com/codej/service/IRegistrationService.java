package com.codej.service;


import com.codej.dto.RegistrationDetailDTO;
import com.codej.model.Registration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IRegistrationService extends ICRUDService<Registration, UUID> {

    Registration saveRegistration(Registration registration) throws  Exception;


    Page<Registration> findAllPaged(Pageable pageable) throws Exception;

    // new methods
    Page<RegistrationDetailDTO> findAllWithDetails(Pageable pageable) throws Exception;
    RegistrationDetailDTO findDetailById(UUID id) throws Exception;

    // --- new kardex method ---
    Page<Object[]> findKardexPaged(Pageable pageable) throws Exception;

    // --- new: kardex with optional DNI filter ---
    Page<Object[]> findKardexPaged(Pageable pageable, String studentDni) throws Exception;

}
