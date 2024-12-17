package com.codej.service.impl;

import com.codej.model.Workshop;
import com.codej.repository.IWorkShopRepository;
import com.codej.service.IWorkshopService;
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
public class WorkshopServiceImpl implements IWorkshopService {
    private final IWorkShopRepository workshopRepository;


    @Override
    public List<Workshop> findAll() {
        return workshopRepository.findAll();
    }

    @Override
    public Workshop findById(Integer id) {
        return workshopRepository.findById(id).orElse(null);
    }

    @Override
    public ResponseEntity<?> save(Workshop workshop) {
        Map<String, Object> response = new HashMap<>();
        Workshop workshopNew = null;
        try {
            workshopNew = workshopRepository.save(workshop);
        }catch (Exception e){
            response.put("mensaje", "Error al crear el taller");
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        response.put("mensaje", "El taller ha sido creado con éxito");
        response.put("taller", workshopNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @Override
    public Workshop update(Workshop workshop) {
        Workshop workshopNew= findById(workshop.getId());
        workshopNew.setName(workshop.getName());
        workshopNew.setDescription(workshop.getDescription());
        workshopNew.setHours(workshop.getHours());
        return workshopRepository.save(workshopNew);
    }

    @Override
    public void delete(Integer id) {
workshopRepository.deleteById(id);
    }

    @Override
    public Page<Workshop> findAll(Pageable pageable) {
        return workshopRepository.findAll(pageable);
    }
}
