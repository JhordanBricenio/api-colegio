package com.codej.service;


import com.codej.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface IUserService extends ICRUDService<User, UUID> {

    User saveUser(User user) throws  Exception;

    User findByDni(String dni) throws Exception;

    ResponseEntity<String> searchByDni(String dni) throws Exception;

    Page<User> findAllPaged(Pageable pageable) throws Exception;

}
