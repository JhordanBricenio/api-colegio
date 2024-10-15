package com.codej.controller;

import com.codej.models.User;
import com.codej.services.IUploadService;
import com.codej.services.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
@AllArgsConstructor
public class UserRestController {
    private final IUserService userService;
    private final IUploadService uploadService;
    @GetMapping("/user")
    public List<User> get(){
        return userService.findAll();
    }

    @PostMapping("/user")
    public ResponseEntity<?> save(@RequestBody User user){
        return userService.save(user);
    }

    @GetMapping("/user/{id}")
    public User getId(@PathVariable Integer id){
        return userService.findById(id);
    }
    @PutMapping("/user/{id}")
    public User update( @RequestBody User user){
        return userService.update(user);
    }

    @DeleteMapping("/user/{id}")
    public void delete(@PathVariable Integer id){
        userService.delete(id);
    }

    @PostMapping("/user/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile archivo, @RequestParam("id") Integer id) {
        Map<String, Object> response = new HashMap<>();
        User user = userService.findById(id);
        if(!archivo.isEmpty()){
            String nombreArchivo= null;
            try {
                nombreArchivo= uploadService.copiar(archivo);
            }catch (IOException e){
                response.put("mensaje", "Error al subir la imagen del usuario ");
                response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            String nombreFotoAnt= user.getPhoto();
            uploadService.eliminar(nombreFotoAnt);
            user.setPhoto(nombreArchivo);
            userService.save(user);
            response.put("user", user);
            response.put("mensaje", "Has subido corectamente la imagen"+nombreArchivo);
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

}
