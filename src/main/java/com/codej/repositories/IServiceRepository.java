package com.codej.repositories;

import com.codej.models.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IServiceRepository extends JpaRepository<Service, Integer> {
}
