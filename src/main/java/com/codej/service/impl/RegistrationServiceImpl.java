package com.codej.service.impl;


import com.codej.dto.RegistrationDetailDTO;
import com.codej.model.Registration;
import com.codej.repository.IGenericRepository;
import com.codej.repository.IRegistrationRepository;
import com.codej.service.IRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl extends CRUDGenericImpl<Registration, UUID> implements IRegistrationService {

    private final IRegistrationRepository userRepository;

    @Override
    protected IGenericRepository<Registration, UUID> getRepository() {
        return userRepository;
    }

    @Override
    public Registration saveRegistration(Registration user) throws Exception {

        return userRepository.save(user);
    }
    
    @Override
    public Page<Registration> findAllPaged(Pageable pageable) throws Exception {
        return userRepository.findAll(pageable);
    }

    @Override
    public Page<RegistrationDetailDTO> findAllWithDetails(Pageable pageable) throws Exception {
        return userRepository.findAllWithDetailsPaged(pageable);
    }

    @Override
    public RegistrationDetailDTO findDetailById(UUID id) throws Exception {
        return userRepository.findDetailById(id);
    }

}
