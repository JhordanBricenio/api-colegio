package com.codej.service.impl;


import com.codej.exceptions.ResourceNotFoundException;
import com.codej.model.User;
import com.codej.model.Workshop;
import com.codej.repository.IGenericRepository;
import com.codej.repository.IWorkshopRepository;
import com.codej.service.IWorkshopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.codej.constants.ErrorMessageConstants.NOT_RESULTS_FOUND_FOR_WITH_ID;


@Service
@RequiredArgsConstructor
public class WorkshopServiceImpl extends CRUDGenericImpl<Workshop, UUID> implements IWorkshopService {

    private final IWorkshopRepository workshopRepository;


    @Override
    protected IGenericRepository<Workshop, UUID> getRepository() {
        return workshopRepository;
    }


}
