package com.codej.service;

import com.codej.model.Degree;

import java.util.List;

public interface IDegreeService {
    public List<Degree> findAll();
    public Degree findById(Integer id);
}
