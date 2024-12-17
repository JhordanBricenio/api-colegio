package com.codej.service.impl;

import com.codej.controller.dto.AssistanceDetailDTO;
import com.codej.controller.dto.AssistanceRequestDTO;
import com.codej.model.Assistance;
import com.codej.model.User;
import com.codej.repository.IAsistenciaRepository;
import com.codej.repository.IUserRepository;
import com.codej.service.IAsistenciaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class AsistenciaServiceImpl implements IAsistenciaService {

    private final IAsistenciaRepository asistenciaRepository;

    private final IUserRepository userRepository;

    @Override
    public List<Assistance> findAll() {
        return asistenciaRepository.findAll();
    }

    @Override
    public Assistance findById(Long id) {
        return asistenciaRepository.findById(id).orElse(null);
    }

    @Override
    public void save(AssistanceRequestDTO assistanceRequest) {

        for (AssistanceDetailDTO detail : assistanceRequest.getAssistanceDetailDTOS()) {
            Assistance asistencia = new Assistance();
            //asistencia.setDate(date);
            asistencia.setStatusAssistance(detail.getStatusAssistance());
            asistencia.setComments(detail.getComments());

            // Buscar al estudiante por ID
            User estudiante = userRepository.findById(detail.getStudentId())
                    .orElseThrow(
                            () -> new RuntimeException("Estudiante no encontrado con ID: "
                                    + detail.getStudentId()));
            asistencia.setUser(estudiante);

            // Guardar la asistencia en la base de datos
            asistenciaRepository.save(asistencia);
        }
    }

    @Override
    public Assistance update(Assistance asistencia, Long id) {
        Assistance asistencia1= findById(id);
        asistencia1.setDate(new Date());
        //asistencia1.setPresente(asistencia.getPresente());
        asistencia1.setUser(asistencia.getUser());
        return asistenciaRepository.save(asistencia1);
    }

    @Override
    public void delete(Long id) {
        asistenciaRepository.deleteById(id);
    }
}
