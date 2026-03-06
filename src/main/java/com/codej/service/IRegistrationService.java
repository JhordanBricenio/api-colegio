package com.codej.service;

import com.codej.model.Registration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IRegistrationService extends ICRUDService<Registration, UUID> {

    Registration saveRegistration(Registration registration) throws Exception;

    List<Registration> findAllWithDetails() throws Exception;

    Page<Registration> findAllWithDetailsPaged(Pageable pageable) throws Exception;
}
