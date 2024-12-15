package com.codej.controller;


import com.codej.controller.dto.RegistrationDTO;
import com.codej.controller.dto.UserDTO;
import com.codej.models.Registration;
import com.codej.models.User;
import com.codej.services.IRegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
@AllArgsConstructor
public class RegistrationRestController {

    private final IRegistrationService licenseService;

    @GetMapping("/registration")
    public List<RegistrationDTO>findAll(){
        return licenseService.findAll();
    }

    @PostMapping("/registration")
    public ResponseEntity<?> save(@RequestBody Registration registration){
        return licenseService.save(registration);
    }

    @GetMapping("/registration/getByDni/{dni}")
    public RegistrationDTO findRegistrationByDni(@PathVariable String dni){
        return licenseService.findRegistrationByDni(dni);
    }

    @GetMapping("/degree/{degreeId}/students")
    public ResponseEntity<List<UserDTO>> obtenerEstudiantesPorGrado(@PathVariable Integer degreeId) {
        List<UserDTO> estudiantes = licenseService.obtenerEstudiantesPorGrado(degreeId);
        return ResponseEntity.ok(estudiantes);
    }

    @GetMapping("/degree/{cursoId}/alumnos")
    public ResponseEntity<List<UserDTO>> findAlumnosByCursoId(@PathVariable Integer cursoId) {
        List<UserDTO> estudiantes = licenseService.findAlumnosByCursoId(cursoId);
        return ResponseEntity.ok(estudiantes);
    }





}
