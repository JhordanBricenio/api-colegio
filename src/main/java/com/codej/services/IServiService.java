package com.codej.services;

import com.codej.models.Servicio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IServiService {
    public List<Servicio> findAll();
    public Servicio findById(Integer id);
    public ResponseEntity<?> save (Servicio service);

    public Servicio update(Servicio service, Integer id);
    public void delete(Integer id);
    public Page<Servicio> findAll(Pageable pageable);
}
