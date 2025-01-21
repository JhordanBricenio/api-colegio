package com.codej.controller;


import com.codej.dto.ServiceDTO;
import com.codej.mapper.ServiceMapper;
import com.codej.model.Services;
import com.codej.service.IServiceService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.codej.constants.ApiConstants.*;

@RestController
@RequestMapping(SERVICE_BASE)
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class ServiceController {

    private final IServiceService serviceService;
    private final ServiceMapper serviceMapper;


    @GetMapping
    public ResponseEntity< List<ServiceDTO>> findAll() throws Exception {
        return ResponseEntity.ok(serviceMapper.toServicesDTOList(serviceService.findAll()));
    }
    @PostMapping
    public ResponseEntity<ServiceDTO> save(@Valid @RequestBody ServiceDTO serviceDTO) throws Exception {
        Services service= serviceMapper.toServicesEntity(serviceDTO);
        Services savedService = serviceService.save(service);
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceMapper.toServicesDTO(savedService));
    }
    @GetMapping(ID_IN_PATH)
    public ResponseEntity<ServiceDTO> findById(@PathVariable UUID id) throws Exception {
        return ResponseEntity.ok(serviceMapper.toServicesDTO(serviceService.findById(id)));
    }

    @PutMapping(ID_IN_PATH)
    public ResponseEntity<ServiceDTO> update(@Valid @RequestBody ServiceDTO serviceDTO,@PathVariable UUID id) throws Exception {
        Services service = serviceMapper.toServicesEntity(serviceDTO);
        Services updatedService = serviceService.update(service, id);
        return ResponseEntity.ok(serviceMapper.toServicesDTO(updatedService));
    }
    @DeleteMapping(ID_IN_PATH)
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws Exception {
        serviceService.delete(id);
        return  ResponseEntity.noContent().build();
    }



}
