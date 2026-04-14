package com.codej.controller;


import com.codej.dto.DniRequest;
import com.codej.dto.UserDTO;
import com.codej.mapper.UserMapper;
import com.codej.model.User;
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

import static com.codej.constants.ApiConstants.ID_IN_PATH;
import static com.codej.constants.ApiConstants.USER_BASE;

@RestController
@RequestMapping(USER_BASE)
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity< List<UserDTO>> findAll() throws Exception {
        userMapper.toUserDTOList(userService.findAll());
        return ResponseEntity.ok(userMapper.toUserDTOList(userService.findAll()));
    }

    @GetMapping("/paged/{page}")
    public Page< UserDTO> findAllPaged(@PathVariable Integer page) throws Exception {
        Pageable pageable = PageRequest.of(page, 8);
        Page<User> userPage = userService.findAllPaged(pageable);
        return userPage.map(userMapper::toUserDTO);
    }

    @PostMapping
    public ResponseEntity<UserDTO> save(@Valid @RequestBody UserDTO userDTO) throws Exception {
        User user= userMapper.toUserEntity(userDTO);
        user.setPassword(userDTO.getDni());
        User savedUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toUserDTO(savedUser));
    }
    @GetMapping(ID_IN_PATH)
    public ResponseEntity<UserDTO> findById(@PathVariable UUID id) throws Exception {
        return ResponseEntity.ok(userMapper.toUserDTO(userService.findById(id)));
    }
    @PostMapping("/dni")
    public ResponseEntity<UserDTO> findByDni(@RequestBody DniRequest dniRequest) throws Exception {
        return ResponseEntity.ok(userMapper.toUserDTO(userService.findByDni(dniRequest.getDni())));
    }

    @PatchMapping(ID_IN_PATH)
    public ResponseEntity<UserDTO> update(@Valid @RequestBody UserDTO userDTO,@PathVariable UUID id) throws Exception {
        User user = userMapper.toUserEntity(userDTO);
        user.setPassword(userDTO.getDni());
        User updatedUser = userService.update(user, id);
        return ResponseEntity.ok(userMapper.toUserDTO(updatedUser));
    }
    @DeleteMapping(ID_IN_PATH)
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws Exception {
        userService.delete(id);
        return  ResponseEntity.noContent().build();
    }

    @GetMapping("/dni/{numero}")
    public ResponseEntity<String> searchByDni(@PathVariable String numero) throws Exception {
       return userService.searchByDni(numero);
    }



}
