package com.codej.service;


import com.codej.model.User;

import java.util.UUID;

public interface IUserService extends ICRUDService<User, UUID> {

    User saveUser(User user) throws  Exception;

    User findByDni(String dni) throws Exception;

}
