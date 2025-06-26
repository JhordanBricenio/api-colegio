package com.codej.controller;


import com.codej.dto.DniRequest;
import com.codej.dto.UserDTO;
import com.codej.mapper.UserMapper;
import com.codej.model.User;
import com.codej.service.IUserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

import static com.codej.constants.ApiConstants.*;

@RestController
@RequestMapping(USER_BASE)
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class UserController {

    private final IUserService userService;
    private final UserMapper userMapper;

    UserController(IUserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Value("${apis.token}")
    private  String apiToken;

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
    @PostMapping("/dni")
    public ResponseEntity<UserDTO> findByDni(@RequestBody DniRequest dniRequest) throws Exception {
        log.info("dni: {}", dniRequest);
        return ResponseEntity.ok(userMapper.toUserDTO(userService.findByDni(dniRequest.getDni())));
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

    @GetMapping("/dni/{numero}")
    public ResponseEntity<String> buscarPorDni(@PathVariable String numero) {
        String url = "https://api.apis.net.pe/v2/reniec/dni?numero=" + numero;

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiToken);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }



}
