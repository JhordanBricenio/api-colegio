package com.codej.controller;


import com.codej.dto.UserDTO;
import com.codej.mapper.UserMapper;
import com.codej.model.User;
import com.codej.service.IUserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.codej.constants.ApiConstants.*;

@RestController
@RequestMapping(USER_BASE)
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class UserController {

    private final IUserService userService;
    private final UserMapper userMapper;


    @GetMapping
    public ResponseEntity< List<UserDTO>> findAll() throws Exception {
        userMapper.toUserDTOList(userService.findAll());
        return ResponseEntity.ok(userMapper.toUserDTOList(userService.findAll()));
    }
    @PostMapping
    public ResponseEntity<UserDTO> save(@Valid @RequestBody UserDTO userDTO) throws Exception {
        User user= userMapper.toUserEntity(userDTO);
        User savedUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toUserDTO(savedUser));
    }
    @GetMapping(ID_IN_PATH)
    public ResponseEntity<UserDTO> findById(@PathVariable UUID id) throws Exception {
        return ResponseEntity.ok(userMapper.toUserDTO(userService.findById(id)));
    }

    @PutMapping(ID_IN_PATH)
    public ResponseEntity<UserDTO> update(@Valid @RequestBody UserDTO userDTO,@PathVariable UUID id) throws Exception {
        User user = userMapper.toUserEntity(userDTO);
        User updatedUser = userService.update(user, id);
        return ResponseEntity.ok(userMapper.toUserDTO(updatedUser));
    }
    @DeleteMapping(ID_IN_PATH)
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws Exception {
        userService.delete(id);
        return  ResponseEntity.noContent().build();
    }



}
