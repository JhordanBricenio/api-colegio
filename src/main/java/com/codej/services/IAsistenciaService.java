package com.codej.services;

import com.codej.controller.dto.AssistanceRequestDTO;
import com.codej.models.Assistance;

import java.util.List;

public interface IAsistenciaService {
    public List<Assistance> findAll();
    public Assistance findById(Long id);
    public  void save (AssistanceRequestDTO assistanceRequest);
    public Assistance update(Assistance asistencia, Long id);
    public void delete(Long id);
}
