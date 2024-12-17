package com.codej.service.impl;

import com.codej.model.Degree;
import com.codej.repository.IDegreeRepository;
import com.codej.service.IDegreeService;
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
