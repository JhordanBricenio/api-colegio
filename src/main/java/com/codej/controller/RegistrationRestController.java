package com.codej.controller;


import com.codej.controller.dto.RegistrationDTO;
import com.codej.models.Registration;
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





}
