package com.codej.service;

import com.codej.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    public List<User> findAll();
    public User findById(UUID id);
    public ResponseEntity<?> save (User user);

    public User update(User user);
    public void delete(UUID id);
    public Page<User> findAll(Pageable pageable);
}
