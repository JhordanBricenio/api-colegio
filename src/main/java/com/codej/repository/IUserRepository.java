package com.codej.repository;

import com.codej.model.User;


import java.util.UUID;

public interface IUserRepository extends IGenericRepository<User, UUID> {

    boolean existsByEmail(String email);
    boolean existsByDni(String dni);
}
