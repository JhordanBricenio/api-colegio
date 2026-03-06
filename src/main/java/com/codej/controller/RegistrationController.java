package com.codej.controller;

import com.codej.dto.RegistrationDTO;
import com.codej.dto.RegistrationSummaryDTO;
import com.codej.mapper.RegistrationMapper;
import com.codej.model.Registration;
import com.codej.service.IRegistrationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.codej.constants.ApiConstants.*;

@RestController
@RequestMapping(REGISTRATION_BASE)
@CrossOrigin(origins = "http://localhost:4200")
public class RegistrationController {

    private final IRegistrationService registrationService;
    private final RegistrationMapper registrationMapper;

    RegistrationController(IRegistrationService registrationService, RegistrationMapper registrationMapper) {
        this.registrationService = registrationService;
        this.registrationMapper = registrationMapper;
    }

    /**
     * Returns all registrations with basic CRUD data (student id, parent id).
     */
    @GetMapping
    public ResponseEntity<List<RegistrationDTO>> findAll() throws Exception {
        return ResponseEntity.ok(registrationMapper.toRegistrationDTOList(registrationService.findAll()));
    }

    /**
     * Returns all registrations enriched with student full name, parent full name,
     * degree and education level information.
     */
    @GetMapping("/summary")
    public ResponseEntity<List<RegistrationSummaryDTO>> findAllSummary() throws Exception {
        return ResponseEntity.ok(registrationService.findAllSummary());
    }

    /**
     * Returns all registrations with full entity details (student, parent, degree, level).
     */
    @GetMapping("/details")
    public ResponseEntity<List<RegistrationDTO>> findAllWithDetails() throws Exception {
        return ResponseEntity.ok(registrationMapper.toRegistrationDTOList(registrationService.findAllWithDetails()));
    }

    @GetMapping("/paged/{page}")
    public Page<RegistrationDTO> findAllPaged(@PathVariable Integer page) throws Exception {
        Pageable pageable = PageRequest.of(page, 8);
        Page<Registration> registrationPage = registrationService.findAllPaged(pageable);
        return registrationPage.map(registrationMapper::toRegistrationDTO);
    }

    @PostMapping
    public ResponseEntity<RegistrationDTO> save(@Valid @RequestBody RegistrationDTO registrationDTO) throws Exception {
        Registration registration = registrationMapper.toRegistrationEntity(registrationDTO);
        Registration saved = registrationService.saveRegistration(registration);
        return ResponseEntity.status(HttpStatus.CREATED).body(registrationMapper.toRegistrationDTO(saved));
    }

    @GetMapping(ID_IN_PATH)
    public ResponseEntity<RegistrationDTO> findById(@PathVariable UUID id) throws Exception {
        return ResponseEntity.ok(registrationMapper.toRegistrationDTO(registrationService.findById(id)));
    }

    /**
     * Returns a single registration with full details: student name, parent name, degree and level.
     */
    @GetMapping(ID_IN_PATH + "/details")
    public ResponseEntity<RegistrationDTO> findByIdWithDetails(@PathVariable UUID id) throws Exception {
        return ResponseEntity.ok(registrationMapper.toRegistrationDTO(registrationService.findByIdWithDetails(id)));
    }

    @PatchMapping(ID_IN_PATH)
    public ResponseEntity<RegistrationDTO> update(@Valid @RequestBody RegistrationDTO registrationDTO,
                                                   @PathVariable UUID id) throws Exception {
        Registration registration = registrationMapper.toRegistrationEntity(registrationDTO);
        Registration updated = registrationService.update(registration, id);
        return ResponseEntity.ok(registrationMapper.toRegistrationDTO(updated));
    }

    @DeleteMapping(ID_IN_PATH)
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws Exception {
        registrationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
