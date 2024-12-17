package com.codej.controller;


import com.codej.model.Servicio;
import com.codej.service.IServiService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ServicioRestController {

    private final IServiService serviceService;

    @GetMapping("/servicios")
    public List<Servicio> findAll(){
        return serviceService.findAll();
    }
    @PostMapping("/servicios")
    public ResponseEntity<?> save(@RequestBody Servicio service){
        return serviceService.save(service);
    }
    @GetMapping("/servicios/{id}")
    public Servicio findById(@PathVariable Integer id){
        return serviceService.findById(id);
    }

    @PutMapping("/servicios/{id}")
    public Servicio update(@RequestBody Servicio service, @PathVariable Integer id){
        return serviceService.update(service, id);
    }

    @DeleteMapping("/servicios/{id}")
    public void delete(@PathVariable Integer id){
        serviceService.delete(id);
    }

}
