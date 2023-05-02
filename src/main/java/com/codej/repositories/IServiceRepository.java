package com.codej.repositories;

import com.codej.models.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IServiceRepository extends JpaRepository<Servicio, Integer> {
}
