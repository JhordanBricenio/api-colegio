package com.codej.service.impl;


import com.codej.exceptions.ResourceNotFoundException;
import com.codej.model.Role;
import com.codej.repository.IGenericRepository;
import com.codej.repository.IRoleRepository;
import com.codej.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends CRUDGenericImpl<Role, UUID> implements IRoleService {

    private final IRoleRepository roleRepository;


    @Override
    protected IGenericRepository<Role, UUID> getRepository() {
        return roleRepository;
    }

    @Override
    public Role findByName(String name) throws Exception {
        return roleRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Role not found with name: " + name));
    }

}
