package com.codej.services.impl;

import com.codej.models.Servicio;
import com.codej.models.User;
import com.codej.repositories.IUserRepository;
import com.codej.services.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {

    private IUserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public ResponseEntity<?> save(User user) {
        Map<String, Object> response = new HashMap<>();
        User userNew = null;
        try {
            userNew = userRepository.save(user);
        }catch (Exception e){
            response.put("mensaje", "Error al crear el usuario");
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        response.put("mensaje", "El usurio ha sido creado con éxito");
        response.put("usuario", userNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @Override
    public User update(User user) {
        User userNew= findById(user.getId());
        userNew.setName(user.getName());
        userNew.setLastname(user.getLastname());
        userNew.setAddress(user.getAddress());
        userNew.setEmail(user.getEmail());
        userNew.setPhone(user.getPhone());
        userNew.setDni(user.getDni());
        userNew.setPassword(user.getPassword());
        return userRepository.save(userNew);
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);

    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
