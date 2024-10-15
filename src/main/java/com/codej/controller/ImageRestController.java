package com.codej.controller;

import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
@AllArgsConstructor
public class ImageRestController {

    @GetMapping("/upload/img/{nombreFoto:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto) {
        Path rutaArchivo = Paths.get("uploads").resolve(nombreFoto).toAbsolutePath();
        Resource recurso= null;
        try{
            recurso= new UrlResource(rutaArchivo.toUri());
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        if (!recurso.exists() && !recurso.isReadable()){
            rutaArchivo = Paths.get("src/main/resources/static/images").resolve("no-image.jpg").toAbsolutePath();
            try{
                recurso= new UrlResource(rutaArchivo.toUri());
            }
            catch (MalformedURLException e){
                e.printStackTrace();
            }
            System.out.println("Error: no se puede cargar la imagen: "+ nombreFoto);
        }
        HttpHeaders cabecera= new HttpHeaders();
        cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+recurso.getFilename()+ "\"");
        return  new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
    }
}
