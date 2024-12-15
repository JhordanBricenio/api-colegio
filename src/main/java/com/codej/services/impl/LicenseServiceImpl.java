package com.codej.services.impl;

import com.codej.controller.dto.RegistrationDTO;
import com.codej.controller.dto.UserDTO;
import com.codej.models.Registration;
import com.codej.models.User;
import com.codej.repositories.IRegistrationRepository;
import com.codej.repositories.IUserRepository;
import com.codej.services.IRegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class LicenseServiceImpl implements IRegistrationService {

    private final IRegistrationRepository licenseRepository;

    private final IUserRepository userRepository;

    @Override
    public List<RegistrationDTO> findAll() {
        return licenseRepository.findAllRegistrations();
    }

    @Override
    public Registration findById(Long id) {
        return licenseRepository.findById(id).orElse(null);
    }

    @Override
    public ResponseEntity<?> save(Registration license) {

        Map<String, Object> response = new HashMap<>();
        Registration licenceNew = null;
        try {
            licenceNew = licenseRepository.save(license);
        }catch (Exception e){
            response.put("mensaje", "Error al crear la matricula");
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        response.put("mensaje", "La matrícula ha sido creada con éxito");
        response.put("matricula", licenceNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @Override
    public Registration update(Registration license, Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {
        licenseRepository.findById(id);

    }

    @Override
    public RegistrationDTO findRegistrationByDni(String dni) {

        RegistrationDTO registration = licenseRepository.findRegistrationByDni(dni)
                .orElseThrow(()
                        -> new RuntimeException("Matrícula no encontrada " +
                        "para el usuario con DNI: " + dni));

        return convertARegistrationDTO(registration);
    }

    private RegistrationDTO convertARegistrationDTO(RegistrationDTO matricula) {
        RegistrationDTO matriculaDTO = new RegistrationDTO();
        matriculaDTO.setId(matricula.getId());
        matriculaDTO.setFechaMatricula(matricula.getFechaMatricula());
        matriculaDTO.setFechaInicio(matricula.getFechaInicio());
        matriculaDTO.setFechaFin(matricula.getFechaFin());
        matriculaDTO.setStatusRegistration(matricula.getStatusRegistration());
        matriculaDTO.setStatusPayment(matricula.getStatusPayment());
        matriculaDTO.setCosto(matricula.getCosto());
        matriculaDTO.setDescuento(matricula.getDescuento());
        matriculaDTO.setPeriodoAcademico(matricula.getPeriodoAcademico());
        matriculaDTO.setTurno(matricula.getTurno());
        matriculaDTO.setSeccion(matricula.getSeccion());
        matriculaDTO.setJornada(matricula.getJornada());

        UserDTO usuarioDTO = new UserDTO();
        usuarioDTO.setId(matricula.getUserDTO().getId());;
        usuarioDTO.setName(matricula.getUserDTO().getName());
        usuarioDTO.setLastname(matricula.getUserDTO().getLastname());
        usuarioDTO.setDni(matricula.getUserDTO().getDni());
        usuarioDTO.setEmail(matricula.getUserDTO().getEmail());;

        matriculaDTO.setUserDTO(usuarioDTO);

        return matriculaDTO;
    }

    public List<UserDTO> obtenerEstudiantesPorGrado(Integer gradoId) {
        return licenseRepository.findUsersByDegreeId(gradoId);
    }

    @Override
    public List<UserDTO> findAlumnosByCursoId(Integer id) {
        return licenseRepository.findAlumnosByCursoId(id);
    }
}
