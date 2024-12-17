package com.codej.controller;

import com.codej.controller.dto.AssistanceRequestDTO;
import com.codej.model.Assistance;
import com.codej.service.IAsistenciaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class AssistanceRestController {
    private final IAsistenciaService assistanceService;

    /*@GetMapping("/asistencia")
    public List<Assistance> findAll() {
        return assistanceService.findAll();
    }*/

   /* @PostMapping("/assistance")
    public ResponseEntity<?> save(@RequestBody List<Assistance> assistance) {
        return assistanceService.save(assistance);
    }*/

    @PostMapping("/assistance")
    public ResponseEntity<String> saveAssistance(
            @RequestBody AssistanceRequestDTO assistanceRequest) {
        assistanceService.save(assistanceRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Asistencias guardadas correctamente.");
    }

    @GetMapping("/assistance/{id}")
    public Assistance findById(@PathVariable Long id) {
        return assistanceService.findById(id);
    }
}
