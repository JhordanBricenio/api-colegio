package com.codej.repositories;

import com.codej.models.Degree;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDegreeRepository extends JpaRepository<Degree, Integer> {
}
