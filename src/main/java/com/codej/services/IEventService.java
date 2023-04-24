package com.codej.services;

import com.codej.models.Course;
import com.codej.models.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IEventService {
    public List<Event> findAll();
    public Event findById(Integer id);
    public Event save (Event event);
    public void delete(Integer id);
    public Page<Event> findAll(Pageable pageable);
}
