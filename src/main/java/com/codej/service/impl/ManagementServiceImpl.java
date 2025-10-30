package com.codej.service.impl;


import com.codej.model.Management;
import com.codej.repository.IGenericRepository;
import com.codej.repository.IManagementRepository;
import com.codej.service.IManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ManagementServiceImpl extends CRUDGenericImpl<Management, UUID> implements IManagementService {

    private final IManagementRepository managementRepository;


    @Override
    protected IGenericRepository<Management, UUID> getRepository() {
        return managementRepository;
    }

}
