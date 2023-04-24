package com.codej.services;

import com.codej.models.Payment;
import com.codej.models.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IServiService {
    public List<Service> findAll();
    public Service findById(Integer id);
    public Service save (Service service);
    public void delete(Integer id);
    public Page<Service> findAll(Pageable pageable);
}
