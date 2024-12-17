package com.codej.service.impl;

import com.codej.model.Servicio;
import com.codej.repository.IServiceRepository;
import com.codej.service.IServiService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ServiServiceImpl implements IServiService {

    private final IServiceRepository servicioRepository;
    @Override
    public List<Servicio> findAll() {
        return servicioRepository.findAll();
    }

    @Override
    public Servicio findById(Integer id) {
        return servicioRepository.findById(id).orElse(null);
    }

    @Override
    public ResponseEntity<?> save(Servicio service) {
        Map<String, Object> response = new HashMap<>();
        Servicio serviceNew = null;
        try {
            serviceNew = servicioRepository.save(service);
        }catch (Exception e){
            response.put("mensaje", "Error al crear el servicio");
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        response.put("mensaje", "El servicio ha sido creado con éxito");
        response.put("servicio", serviceNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @Override
    public Servicio update(Servicio service, Integer id) {
        Servicio serviceUpdate = findById(service.getId());
        serviceUpdate.setName(service.getName());
        serviceUpdate.setDescription(service.getDescription());
        return servicioRepository.save(service);
    }

    @Override
    public void delete(Integer id) {
        servicioRepository.deleteById(id);

    }

    @Override
    public Page<Servicio> findAll(Pageable pageable) {
        return servicioRepository.findAll(pageable);
    }
}
