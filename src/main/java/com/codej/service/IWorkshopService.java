package com.codej.service;

import com.codej.model.Workshop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IWorkshopService {
    public List<Workshop> findAll();
    public Workshop findById(Integer id);
    public ResponseEntity<?> save (Workshop workshop);
    public Workshop update(Workshop workshop);
    public void delete(Integer id);
    public Page<Workshop> findAll(Pageable pageable);
}
