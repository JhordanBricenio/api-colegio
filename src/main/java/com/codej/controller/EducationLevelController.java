package com.codej.controller;

import com.codej.dto.EducationLevelDTO;
import com.codej.mapper.EducationLevelMapper;
import com.codej.model.EducationLevel;
import com.codej.service.IEducationLevelService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.codej.constants.ApiConstants.ID_IN_PATH;
import static com.codej.constants.ApiConstants.EDUCATION_LEVEL_BASE;

@RestController
@RequestMapping(EDUCATION_LEVEL_BASE)
@AllArgsConstructor
public class EducationLevelController {
    
    private final IEducationLevelService educationLevelService;
    private final EducationLevelMapper educationLevelMapper;


    @GetMapping
    public ResponseEntity<List<EducationLevelDTO>> findAll() throws Exception {
        return ResponseEntity.ok(educationLevelMapper.mapIn(educationLevelService.findAll()));
    }

    @PostMapping
    public ResponseEntity<EducationLevelDTO> save(@Valid @RequestBody EducationLevelDTO educationLevelDTO) throws Exception {
        EducationLevel educationLevel= educationLevelMapper.toEducationLevelEntity(educationLevelDTO);
        if (educationLevel.getName() != null) {
            educationLevel.setName(educationLevel.getName().toUpperCase());
        }
        EducationLevel savedEducationLevel = educationLevelService.save(educationLevel);
        return ResponseEntity.status(HttpStatus.CREATED).body(educationLevelMapper.toEducationLevelDTO(savedEducationLevel));
    }
    @GetMapping(ID_IN_PATH)
    public ResponseEntity<EducationLevelDTO> findById(@PathVariable UUID id) throws Exception {
        return ResponseEntity.ok(educationLevelMapper.toEducationLevelDTO(educationLevelService.findById(id)));
    }

    @PatchMapping(ID_IN_PATH)
    public ResponseEntity<EducationLevelDTO> update(@Valid @RequestBody EducationLevelDTO educationLevelDTO,@PathVariable UUID id)
            throws Exception {
        EducationLevel educationLevel = educationLevelMapper.toEducationLevelEntity(educationLevelDTO);
        EducationLevel updatedEducationLevel = educationLevelService.update(educationLevel, id);
        return ResponseEntity.ok(educationLevelMapper.toEducationLevelDTO(updatedEducationLevel));
    }
    @DeleteMapping(ID_IN_PATH)
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws Exception {
        educationLevelService.delete(id);
        return  ResponseEntity.noContent().build();
    }
}
