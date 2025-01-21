package com.codej.service.impl;


import com.codej.model.Services;
import com.codej.repository.IGenericRepository;
import com.codej.repository.IServiceRepository;
import com.codej.service.IServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ServiceServiceImpl extends CRUDGenericImpl<Services, UUID> implements IServiceService {

    private final IServiceRepository postRepository;


    @Override
    protected IGenericRepository<Services, UUID> getRepository() {
        return postRepository;
    }
}
