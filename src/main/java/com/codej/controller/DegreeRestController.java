package com.codej.controller;

import com.codej.model.Degree;
import com.codej.service.IDegreeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
@AllArgsConstructor
public class DegreeRestController {

    private final IDegreeService degreeService;

    @GetMapping("/degrees")
    public List<Degree> findAll(){
        return degreeService.findAll();
    }
    @GetMapping("/degrees/{id}")
    public Degree findById(@PathVariable Integer id){
        return degreeService.findById(id);
    }
}
