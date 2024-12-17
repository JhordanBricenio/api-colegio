package com.codej.service;

import com.codej.controller.dto.AssistanceRequestDTO;
import com.codej.model.Assistance;

import java.util.List;

public interface IAsistenciaService {
    public List<Assistance> findAll();
    public Assistance findById(Long id);
    public  void save (AssistanceRequestDTO assistanceRequest);
    public Assistance update(Assistance asistencia, Long id);
    public void delete(Long id);
}
