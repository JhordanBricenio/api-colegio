package com.codej.controller;


import com.codej.dto.DniRequest;
import com.codej.dto.ParentDTO;
import com.codej.mapper.ParentMapper;
import com.codej.model.Parent;
import com.codej.service.IParentService;
import com.codej.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequestMapping(PARENT_BASE)
@RequiredArgsConstructor
public class ParentController {

    private final IParentService parentService;
    private final ParentMapper parentMapper;
    private final IUserService userService;

    @GetMapping
    public ResponseEntity< List<ParentDTO>> findAll() throws Exception {
        parentMapper.toParentDTOList(parentService.findAll());
        return ResponseEntity.ok(parentMapper.toParentDTOList(parentService.findAll()));
    }

    @GetMapping("/paged/{page}")
    public Page< ParentDTO> findAllPaged(@PathVariable Integer page) throws Exception {
        Pageable pageable = PageRequest.of(page, 8);
        Page<Parent> parentPage = parentService.findAllPaged(pageable);
        return parentPage.map(parentMapper::toParentDTO);
    }

    @PostMapping
    public ResponseEntity<ParentDTO> save(@Valid @RequestBody ParentDTO parentDTO) throws Exception {
        Parent parent= parentMapper.toParentEntity(parentDTO);
        parent.getUser().setPassword(parentDTO.getUser().getDni());
        Parent savedParent = parentService.saveParent(parent);
        return ResponseEntity.status(HttpStatus.CREATED).body(parentMapper.toParentDTO(savedParent));
    }
    @GetMapping(ID_IN_PATH)
    public ResponseEntity<ParentDTO> findById(@PathVariable UUID id) throws Exception {
        return ResponseEntity.ok(parentMapper.toParentDTO(parentService.findById(id)));
    }
    @PostMapping("/dni")
    public ResponseEntity<ParentDTO> findByDni(@RequestBody DniRequest dniRequest) throws Exception {
        return ResponseEntity.ok(parentMapper.toParentDTO(parentService.findByDni(dniRequest.getDni())));
    }

    @PatchMapping(ID_IN_PATH)
    public ResponseEntity<ParentDTO> update(@Valid @RequestBody ParentDTO parentDTO,@PathVariable UUID id) throws Exception {
        Parent parent = parentMapper.toParentEntity(parentDTO);
        parent.getUser().setPassword(parentDTO.getUser().getDni());
        Parent updatedParent = parentService.update(parent, id);
        return ResponseEntity.ok(parentMapper.toParentDTO(updatedParent));
    }
    @DeleteMapping(ID_IN_PATH)
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws Exception {
        parentService.delete(id);
        return  ResponseEntity.noContent().build();
    }

    @GetMapping("/dni/{numero}")
    public ResponseEntity<String> searchByDni(@PathVariable String numero) throws Exception {
       return userService.searchByDni(numero);
    }



}
