package com.codej.controller;

import com.codej.model.Event;
import com.codej.service.IEventService;
import com.codej.service.IUploadService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
@AllArgsConstructor
public class EventRestContrller {

    private final IEventService eventService;
    private final IUploadService uploadService;

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

    @PostMapping("/event/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile archivo, @RequestParam("id") Integer id) {
        Map<String, Object> response = new HashMap<>();
        Event event = eventService.findById(id);
        if(!archivo.isEmpty()){
            String nombreArchivo= null;
            try {
                nombreArchivo= uploadService.copiar(archivo);
            }catch (IOException e){
                response.put("mensaje", "Error al subir la imagen del eventp ");
                response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            String nombreFotoAnt= event.getImage();
            uploadService.eliminar(nombreFotoAnt);
            event.setImage(nombreArchivo);
            eventService.save(event);
            response.put("event", event);
            response.put("mensaje", "Has subido corectamente la imagen"+nombreArchivo);
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }


}
