package com.codej.services;

import com.codej.models.Asistencia;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IAsistenciaService {
    public List<Asistencia> findAll();
    public Asistencia findById(Long id);
    public  ResponseEntity<?> save (List<Asistencia> asistencias);
    public Asistencia update(Asistencia asistencia, Long id);
    public void delete(Long id);
}
