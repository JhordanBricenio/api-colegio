package com.codej.services.impl;

import com.codej.services.IServiService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiServiceImpl implements IServiService {
    @Override
    public List<com.codej.models.Service> findAll() {
        return null;
    }

    @Override
    public com.codej.models.Service findById(Integer id) {
        return null;
    }

    @Override
    public com.codej.models.Service save(com.codej.models.Service service) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Page<com.codej.models.Service> findAll(Pageable pageable) {
        return null;
    }
}
