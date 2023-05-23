package com.codej.services.impl;

import com.codej.models.Asistencia;
import com.codej.repositories.IAsistenciaRepository;
import com.codej.services.IAsistenciaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class AsistenciaServiceImpl implements IAsistenciaService {

    private final IAsistenciaRepository asistenciaRepository;

    @Override
    public List<Asistencia> findAll() {
        return asistenciaRepository.findAll();
    }

    @Override
    public Asistencia findById(Long id) {
        return asistenciaRepository.findById(id).orElse(null);
    }

    @Override
    public ResponseEntity<?> save(List<Asistencia> asistencias) {
        List<Asistencia> asistenciasGuardadas = asistenciaRepository.saveAll(asistencias);

        if (asistenciasGuardadas.isEmpty()) {
            return ResponseEntity.badRequest().body("Error al guardar las asistencias");
        }

        return ResponseEntity.ok("Asistencias registradas exitosamente");
    }

    @Override
    public Asistencia update(Asistencia asistencia, Long id) {
        Asistencia asistencia1= findById(id);
        asistencia1.setFecha(new Date());
        asistencia1.setPresente(asistencia.getPresente());
        asistencia1.setUser(asistencia.getUser());
        return asistenciaRepository.save(asistencia1);
    }

    @Override
    public void delete(Long id) {
        asistenciaRepository.deleteById(id);
    }
}
