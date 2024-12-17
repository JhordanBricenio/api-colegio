package com.codej.controller;

import com.codej.constants.ApiConstants;
import com.codej.dto.TagDTO;
import com.codej.service.ITagService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ApiConstants.Tag.BASE)
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class TagController {

    private final ITagService serviceService;


    @GetMapping
    public ResponseEntity< List<TagDTO>> findAll(){
        return ResponseEntity.ok( serviceService.findAll());
    }
    @PostMapping
    public ResponseEntity<TagDTO> save(@RequestBody TagDTO service){
        return new ResponseEntity<>(serviceService.save(service), HttpStatus.CREATED);
    }
    @GetMapping(ApiConstants.Tag.IN_ID_PATH)
    public ResponseEntity<TagDTO> findById(@PathVariable UUID id){
        return  ResponseEntity.ok(serviceService.findById(id));
    }

    @PutMapping(ApiConstants.Tag.IN_ID_PATH)
    public ResponseEntity<TagDTO> update(@RequestBody TagDTO service,@PathVariable UUID id){
        return ResponseEntity.ok(serviceService.update(service, id));
    }
    @DeleteMapping(ApiConstants.Tag.IN_ID_PATH)
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        serviceService.delete(id);
        return  ResponseEntity.noContent().build();
    }



}
