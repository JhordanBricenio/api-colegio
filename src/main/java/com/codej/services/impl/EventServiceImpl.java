package com.codej.services.impl;

import com.codej.models.Course;
import com.codej.models.Event;
import com.codej.repositories.IEventRepository;
import com.codej.services.IEventService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class EventServiceImpl implements IEventService {

    private  final IEventRepository eventRepository;

    @Override
    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    @Override
    public Event findById(Integer id) {
        return eventRepository.findById(id).orElse(null);
    }

    @Override
    public ResponseEntity<?> save(Event event) {
        Map<String, Object> response = new HashMap<>();
        Event eventNew = null;
        try {
            eventNew = eventRepository.save(event);
        }catch (Exception e){
            response.put("mensaje", "Error al crear el evento");
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        response.put("mensaje", "El evento ha sido creado con éxito");
        response.put("proyecto", eventNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @Override
    public Event update(Event event, Integer id) {
        Event eventUpdate = findById(event.getId());
        eventUpdate.setName(event.getName());
        eventUpdate.setCreated_at(event.getCreated_at());
        eventUpdate.setDate(event.getDate());
        eventUpdate.setDescription(event.getDescription());
        eventUpdate.setLink(event.getLink());
        eventUpdate.setPlace(event.getPlace());
        eventUpdate.setStatus(event.getStatus());
       // eventUpdate.setTime(event.getTime());
        return eventRepository.save(event);
    }

    @Override
    public void delete(Integer id) {
        eventRepository.deleteById(id);
    }

    @Override
    public Page<Event> findAll(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }
}
