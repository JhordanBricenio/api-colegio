package com.codej.services.impl;

import com.codej.models.Degree;
import com.codej.repositories.IDegreeRepository;
import com.codej.services.IDegreeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DegreeServiceImpl implements IDegreeService {

    private final IDegreeRepository degreeRepository;


    @Override
    public List<Degree> findAll() {
        return degreeRepository.findAll();
    }

    @Override
    public Degree findById(Integer id) {
        return degreeRepository.findById(id).orElse(null);
    }
}
