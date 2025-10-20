package com.codej.repository;

import com.codej.model.Role;

import java.util.Optional;
import java.util.UUID;

public interface IRoleRepository extends IGenericRepository<Role, UUID> {

    Optional<Role> findByName(String name);

}
