package com.codej.repositories;

import com.codej.models.Workshop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IWorkShopRepository extends JpaRepository<Workshop, Integer> {
}
