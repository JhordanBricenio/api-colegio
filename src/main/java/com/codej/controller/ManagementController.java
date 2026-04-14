package com.codej.controller;

import com.codej.dto.ManagementDTO;
import com.codej.mapper.ManagementMapper;
import com.codej.model.Management;
import com.codej.service.IManagementService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.codej.constants.ApiConstants.*;

@RestController
@RequestMapping(MANAGEMENT_BASE)
@AllArgsConstructor
public class ManagementController {
    
    private final IManagementService managementService;
    private final ManagementMapper managementMapper;


    @GetMapping
    public ResponseEntity<List<ManagementDTO>> findAll() throws Exception {
        return ResponseEntity.ok(managementMapper.mapIn(managementService.findAll()));
    }

    @PostMapping
    public ResponseEntity<ManagementDTO> save(@Valid @RequestBody ManagementDTO managementDTO) throws Exception {
        Management management= managementMapper.mapOut(managementDTO);
        if (management.getName() != null) {
            management.setName(management.getName().toUpperCase());
        }
        Management savedManagement = managementService.save(management);
        return ResponseEntity.status(HttpStatus.CREATED).body(managementMapper.mapIn(savedManagement));
    }
    @GetMapping(ID_IN_PATH)
    public ResponseEntity<ManagementDTO> findById(@PathVariable UUID id) throws Exception {
        return ResponseEntity.ok(managementMapper.mapIn(managementService.findById(id)));
    }

    @PatchMapping(ID_IN_PATH)
    public ResponseEntity<ManagementDTO> update(@Valid @RequestBody ManagementDTO managementDTO,@PathVariable UUID id)
            throws Exception {
        Management management = managementMapper.mapOut(managementDTO);
        Management updatedManagement = managementService.update(management, id);
        return ResponseEntity.ok(managementMapper.mapIn(updatedManagement));
    }
    @DeleteMapping(ID_IN_PATH)
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws Exception {
        managementService.delete(id);
        return  ResponseEntity.noContent().build();
    }
}
