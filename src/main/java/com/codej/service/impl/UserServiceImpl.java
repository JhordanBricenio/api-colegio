package com.codej.service.impl;


import com.codej.exceptions.DuplicateResourceException;
import com.codej.exceptions.ResourceNotFoundException;
import com.codej.model.User;
import com.codej.repository.IGenericRepository;
import com.codej.repository.IUserRepository;
import com.codej.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserServiceImpl extends CRUDGenericImpl<User, UUID> implements IUserService {

    private final IUserRepository userRepository;


    @Override
    protected IGenericRepository<User, UUID> getRepository() {
        return userRepository;
    }

    @Override
    public User saveUser(User user) throws Exception {
        if (userRepository.existsByEmail(user.getEmail())) {
            //super(String.format("%s con %s '%s' ya existe", resourceName, fieldName, fieldValue));
            throw new DuplicateResourceException(User.class.getSimpleName(), "email", user.getEmail());
        }
        if (userRepository.existsByDni(user.getDni())) {
            throw new DuplicateResourceException(User.class.getSimpleName(), "dni", user.getDni());
        }
        return userRepository.save(user);
    }

    @Override
    public User findByDni(String dni) throws Exception {
        if (!userRepository.existsByDni(dni)) {
            throw new ResourceNotFoundException("No existe un usuario con el dni: " + dni);
        }
        return userRepository.findByDni(dni);
    }

}
