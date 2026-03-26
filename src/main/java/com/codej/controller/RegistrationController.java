package com.codej.controller;


import com.codej.dto.RegistrationDTO;
import com.codej.dto.RegistrationDetailDTO;
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

import static com.codej.constants.ApiConstants.ID_IN_PATH;
import static com.codej.constants.ApiConstants.REGISTRATION_BASE;

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
    @GetMapping
    public ResponseEntity< List<RegistrationDTO>> findAll() throws Exception {
        registrationMapper.toRegistrationDTOList(registrationService.findAll());
        return ResponseEntity.ok(registrationMapper.toRegistrationDTOList(registrationService.findAll()));
    }

    @GetMapping("/paged/{page}")
    public Page< RegistrationDTO> findAllPaged(@PathVariable Integer page) throws Exception {
        Pageable pageable = PageRequest.of(page, 8);
        Page<Registration> registrationPage = registrationService.findAllPaged(pageable);
        return registrationPage.map(registrationMapper::toRegistrationDTO);
    }

    @GetMapping("/details/paged/{page}")
    public Page< RegistrationDetailDTO> findAllDetailsPaged(@PathVariable Integer page) throws Exception {
        Pageable pageable = PageRequest.of(page, 8);
        return registrationService.findAllWithDetails(pageable);
    }

    @PostMapping
    public ResponseEntity<RegistrationDTO> save(@Valid @RequestBody RegistrationDTO registrationDTO) throws Exception {
        Registration registration= registrationMapper.toRegistrationEntity(registrationDTO);
        Registration savedRegistration = registrationService.saveRegistration(registration);
        return ResponseEntity.status(HttpStatus.CREATED).body(registrationMapper.toRegistrationDTO(savedRegistration));
    }
    @GetMapping(ID_IN_PATH)
    public ResponseEntity<RegistrationDTO> findById(@PathVariable UUID id) throws Exception {
        return ResponseEntity.ok(registrationMapper.toRegistrationDTO(registrationService.findById(id)));
    }

    @GetMapping("/details" + ID_IN_PATH)
    public ResponseEntity<RegistrationDetailDTO> findDetailById(@PathVariable UUID id) throws Exception {
        return ResponseEntity.ok(registrationService.findDetailById(id));
    }
//    @PostMapping("/dni")
//    public ResponseEntity<RegistrationDTO> findByDni(@RequestBody DniRequest dniRequest) throws Exception {
//        return ResponseEntity.ok(registrationMapper.toRegistrationDTO(registrationService.findByDni(dniRequest.getDni())));
//    }

    @PatchMapping(ID_IN_PATH)
    public ResponseEntity<RegistrationDTO> update(@Valid @RequestBody RegistrationDTO registrationDTO,@PathVariable UUID id) throws Exception {
        Registration registration = registrationMapper.toRegistrationEntity(registrationDTO);
        Registration updatedRegistration = registrationService.update(registration, id);
        return ResponseEntity.ok(registrationMapper.toRegistrationDTO(updatedRegistration));
    }
    @DeleteMapping(ID_IN_PATH)
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws Exception {
        registrationService.delete(id);
        return  ResponseEntity.noContent().build();
    }

//    @GetMapping("/dni/{numero}")
//    public ResponseEntity<String> searchByDni(@PathVariable String numero) throws Exception {
//       return userService.searchByDni(numero);
//    }



}
