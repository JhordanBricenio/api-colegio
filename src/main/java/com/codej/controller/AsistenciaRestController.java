package com.codej.controller;

import com.codej.models.Asistencia;
import com.codej.services.IAsistenciaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class AsistenciaRestController {
    private final IAsistenciaService asistenciaService;

    @GetMapping("/asistencia")
    public List<Asistencia> findAll() {
        return asistenciaService.findAll();
    }

    @PostMapping("/asistencias")
    public ResponseEntity<?> save(@RequestBody List<Asistencia> asistencias) {
        return asistenciaService.save(asistencias);
    }

    @GetMapping("/asistencia/{id}")
    public Asistencia findById(@PathVariable Long id) {
        return asistenciaService.findById(id);
    }
}
