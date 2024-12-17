package com.codej.repository;

import com.codej.model.Degree;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDegreeRepository extends JpaRepository<Degree, Integer> {
}
