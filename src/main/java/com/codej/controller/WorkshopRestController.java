package com.codej.controller;

import com.codej.model.Workshop;
import com.codej.service.IWorkshopService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
@AllArgsConstructor
public class WorkshopRestController {

    private final IWorkshopService workshopService;

    @GetMapping("/workshop")
    public List<Workshop> finAll(){
        return workshopService.findAll();
    }
    @PostMapping("/workshop")
    public ResponseEntity<?> save(@RequestBody Workshop workshop){
        return workshopService.save(workshop);
    }
    @GetMapping("/workshop/{id}")
    public Workshop getId(@PathVariable Integer id){
        return workshopService.findById(id);
    }
    @PutMapping("/workshop/{id}")
    public Workshop update(@RequestBody Workshop workshop){
        return workshopService.update(workshop);
    }

    @DeleteMapping("/workshop/{id}")
    public void delete(@PathVariable Integer id){
        workshopService.delete(id);
    }


}
