package com.codej.service.impl;


import com.codej.model.EducationLevel;
import com.codej.repository.IGenericRepository;
import com.codej.repository.IEducationLevelRepository;
import com.codej.service.IEducationLevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class EducationLevelServiceImpl extends CRUDGenericImpl<EducationLevel, UUID> implements IEducationLevelService {

    private final IEducationLevelRepository educationLevelRepository;


    @Override
    protected IGenericRepository<EducationLevel, UUID> getRepository() {
        return educationLevelRepository;
    }

}
