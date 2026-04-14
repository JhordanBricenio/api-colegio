package com.codej.service.impl;


import com.codej.exceptions.DuplicateResourceException;
import com.codej.exceptions.ResourceNotFoundException;
import com.codej.model.Role;
import com.codej.model.User;
import com.codej.repository.IGenericRepository;
import com.codej.repository.IRoleRepository;
import com.codej.repository.IUserRepository;
import com.codej.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserServiceImpl extends CRUDGenericImpl<User, UUID> implements IUserService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${apis.token}")
    private  String apiToken;


    @Override
    protected IGenericRepository<User, UUID> getRepository() {
        return userRepository;
    }

    @Override
    public User saveUser(User user) throws Exception {
        Role role = roleRepository.findById(user.getRole().getIdRole())
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el rol con id: " + user.getRole().getIdRole()));
        user.setRole(role);
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateResourceException(User.class.getSimpleName(), "email", user.getEmail());
        }
        if (userRepository.existsByDni(user.getDni())) {
            throw new DuplicateResourceException(User.class.getSimpleName(), "dni", user.getDni());
        }
        // encode password before saving
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
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

    @Override
    public ResponseEntity<String> searchByDni(String dni) throws Exception {
        String url = "https://api.apis.net.pe/v2/reniec/dni?numero=" + dni;

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiToken);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @Override
    public Page<User> findAllPaged(Pageable pageable) throws Exception {
        return userRepository.findAll(pageable);
    }

}
