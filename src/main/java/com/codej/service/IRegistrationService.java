package com.codej.service;

import com.codej.model.Registration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IRegistrationService extends ICRUDService<Registration, UUID> {

    Registration saveRegistration(Registration registration, UUID idStudent, UUID idParent) throws Exception;

    Page<Registration> findAllPaged(Pageable pageable) throws Exception;
}
