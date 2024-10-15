package com.codej.repositories;

import com.codej.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Integer> {

   // User finByDni(String dni);
}
