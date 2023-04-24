package com.codej.repositories;

import com.codej.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEventRepository extends JpaRepository<Event, Integer> {
}
