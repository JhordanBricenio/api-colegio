package com.codej.service.impl;


import com.codej.exceptions.ResourceNotFoundException;
import com.codej.model.Role;
import com.codej.model.Parent;
import com.codej.repository.IGenericRepository;
import com.codej.repository.IRoleRepository;
import com.codej.repository.IParentRepository;
import com.codej.service.IParentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ParentServiceImpl extends CRUDGenericImpl<Parent, UUID> implements IParentService {

    private final IParentRepository parentRepository;
    private final IRoleRepository roleRepository;

    @Value("${apis.token}")
    private  String apiToken;


    @Override
    protected IGenericRepository<Parent, UUID> getRepository() {
        return parentRepository;
    }

    @Override
    public Parent saveParent(Parent parent) throws Exception {
        Role role = roleRepository.findById(parent.getUser().getRole().getIdRole())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el rol con id: " + parent.getUser().getRole().getIdRole()));
        parent.getUser().setRole(role);
        return parentRepository.save(parent);
    }

    @Override
    public Parent findByDni(String dni) throws Exception {
        return parentRepository.findParentByDni(dni)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el docente con DNI: " + dni));
    }

    @Override
    public Page<Parent> findAllPaged(Pageable pageable) throws Exception {
        return parentRepository.findAll(pageable);
    }

}
