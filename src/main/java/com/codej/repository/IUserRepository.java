package com.codej.repository;

import com.codej.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IUserRepository extends JpaRepository<User, UUID> {

   // User finByDni(String dni);
}
