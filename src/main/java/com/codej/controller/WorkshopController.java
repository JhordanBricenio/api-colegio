package com.codej.controller;


import com.codej.dto.WorkshopDTO;
import com.codej.mapper.UserMapper;
import com.codej.mapper.WorkshopMapper;
import com.codej.model.Workshop;
import com.codej.service.IWorkshopService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.codej.constants.ApiConstants.*;

@RestController
@RequestMapping(WORKSHOP_BASE)
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class WorkshopController {

    private final IWorkshopService workshopService;
    private final WorkshopMapper workshopMapper;

    @GetMapping
    public ResponseEntity< List<WorkshopDTO>> findAll() throws Exception {
        workshopMapper.toWorkshopDTOList(workshopService.findAll());
        return ResponseEntity.ok(workshopMapper.toWorkshopDTOList(workshopService.findAll()));
    }
    @PostMapping
    public ResponseEntity<WorkshopDTO> save(@Valid @RequestBody WorkshopDTO workshopDTO) throws Exception {
        Workshop workshop= workshopMapper.toWorkshopEntity(workshopDTO);
        Workshop savedWorkshop = workshopService.save(workshop);
        return ResponseEntity.status(HttpStatus.CREATED).body(workshopMapper.toWorkshopDTO(savedWorkshop));
    }
    @GetMapping(ID_IN_PATH)
    public ResponseEntity<WorkshopDTO> findById(@PathVariable UUID id) throws Exception {
        return ResponseEntity.ok(workshopMapper.toWorkshopDTO(workshopService.findById(id)));
    }

    @PutMapping(ID_IN_PATH)
    public ResponseEntity<WorkshopDTO> update(@Valid @RequestBody WorkshopDTO workshopDTO,@PathVariable UUID id) throws Exception {
        Workshop workshop = workshopMapper.toWorkshopEntity(workshopDTO);
        Workshop updatedWorkshop = workshopService.update(workshop, id);
        return ResponseEntity.ok(workshopMapper.toWorkshopDTO(updatedWorkshop));
    }
    @DeleteMapping(ID_IN_PATH)
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws Exception {
        workshopService.delete(id);
        return  ResponseEntity.noContent().build();
    }



}
