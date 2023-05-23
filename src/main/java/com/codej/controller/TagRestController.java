package com.codej.controller;

import com.codej.models.Tag;
import com.codej.services.ITagService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class TagRestController {

    private final ITagService serviceService;


    @GetMapping("/tags")
    public List<Tag> findAll(){
        return serviceService.findAll();
    }
    @PostMapping("/tags")
    public Tag save(@RequestBody Tag service){
        return serviceService.save(service);
    }
    @GetMapping("/tags/{id}")
    public Tag findById(@PathVariable Integer id){
        return serviceService.findById(id);
    }

    @PutMapping("/tags/{id}")
    public Tag update(@RequestBody Tag service,@PathVariable Integer id){
        return serviceService.update(service, id);
    }

    @DeleteMapping("/tags/{id}")
    public void delete(@PathVariable Integer id){
        serviceService.delete(id);
    }



}
