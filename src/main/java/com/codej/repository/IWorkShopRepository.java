package com.codej.repository;

import com.codej.model.Workshop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IWorkShopRepository extends JpaRepository<Workshop, Integer> {
}
