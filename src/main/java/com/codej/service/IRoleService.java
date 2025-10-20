package com.codej.service;


import com.codej.model.Role;

import java.util.UUID;

public interface IRoleService extends ICRUDService<Role, UUID> {

    Role findByName(String name) throws Exception;

}
