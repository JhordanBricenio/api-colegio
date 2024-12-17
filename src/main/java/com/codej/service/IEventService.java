package com.codej.service;

import com.codej.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IEventService {
    public List<Event> findAll();
    public Event findById(Integer id);
    public ResponseEntity<?> save (Event event);

    public Event update (Event event, Integer id);
    public void delete(Integer id);
    public Page<Event> findAll(Pageable pageable);
}
