package com.codej.services;

import com.codej.models.Degree;

import java.util.List;

public interface IDegreeService {
    public List<Degree> findAll();
    public Degree findById(Integer id);
}
