package com.codej.repository;

import com.codej.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IServiceRepository extends JpaRepository<Servicio, Integer> {
}
