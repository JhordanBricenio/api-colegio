package com.codej.controller;

import com.codej.dto.RoleDTO;
import com.codej.mapper.RoleMapper;
import com.codej.model.Role;
import com.codej.service.IRoleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.codej.constants.ApiConstants.ID_IN_PATH;
import static com.codej.constants.ApiConstants.ROLE_BASE;

@RestController
@RequestMapping(ROLE_BASE)
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class RoleController {
    
    private final IRoleService roleService;
    private final RoleMapper roleMapper;


    @GetMapping
    public ResponseEntity<List<RoleDTO>> findAll() throws Exception {
        return ResponseEntity.ok(roleMapper.mapIn(roleService.findAll()));
    }
    @GetMapping("/search/{name}")
    public ResponseEntity<RoleDTO> findByName(@PathVariable String name) throws Exception {
        return ResponseEntity.ok(roleMapper.mapIn(roleService.findByName(name)));
    }

    @PostMapping
    public ResponseEntity<RoleDTO> save(@Valid @RequestBody RoleDTO roleDTO) throws Exception {
        Role role= roleMapper.mapOut(roleDTO);
        if (role.getName() != null) {
            role.setName(role.getName().toUpperCase());
        }
        Role savedRole = roleService.save(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(roleMapper.mapIn(savedRole));
    }
    @GetMapping(ID_IN_PATH)
    public ResponseEntity<RoleDTO> findById(@PathVariable UUID id) throws Exception {
        return ResponseEntity.ok(roleMapper.mapIn(roleService.findById(id)));
    }

    @PatchMapping(ID_IN_PATH)
    public ResponseEntity<RoleDTO> update(@Valid @RequestBody RoleDTO roleDTO,@PathVariable UUID id)
            throws Exception {
        Role role = roleMapper.mapOut(roleDTO);
        Role updatedRole = roleService.update(role, id);
        return ResponseEntity.ok(roleMapper.mapIn(updatedRole));
    }
    @DeleteMapping(ID_IN_PATH)
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws Exception {
        roleService.delete(id);
        return  ResponseEntity.noContent().build();
    }
}
