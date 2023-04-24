package com.codej.services;

import com.codej.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService {
    public List<User> findAll();
    public User findById(Integer id);
    public User save (User user);
    public void delete(Integer id);
    public Page<User> findAll(Pageable pageable);
}
