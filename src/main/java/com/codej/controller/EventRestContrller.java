package com.codej.controller;


import com.codej.models.Event;
import com.codej.services.IEventService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
@AllArgsConstructor
public class EventRestContrller {

    private final IEventService eventService;

    @GetMapping("/event")
    public List<Event> findAll() {
        return eventService.findAll();
    }
    @PostMapping("/event")
    public ResponseEntity<?> save(@RequestBody Event event) {
        return eventService.save(event);
    }
    @GetMapping("/event/{id}")
    public Event findById(@PathVariable Integer id) {
        return eventService.findById(id);
    }
    @PutMapping("/event/{id}")
    public Event update(@RequestBody Event event, @PathVariable Integer id) {
        return eventService.update(event, id);
    }
    @DeleteMapping("/event/{id}")
    public void delete(@PathVariable Integer id) {
        eventService.delete(id);
    }

}
