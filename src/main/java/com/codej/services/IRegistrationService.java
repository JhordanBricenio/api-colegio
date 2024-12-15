package com.codej.services;

import com.codej.controller.dto.RegistrationDTO;
import com.codej.controller.dto.UserDTO;
import com.codej.models.Registration;

import com.codej.models.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IRegistrationService {
     List<RegistrationDTO> findAll();
     Registration findById(Long id);
     ResponseEntity<?> save (Registration license);
     Registration update(Registration license, Long id);
     void delete(Long id);

     RegistrationDTO findRegistrationByDni(String dni);


     List<UserDTO>obtenerEstudiantesPorGrado(Integer id);
     List<UserDTO>findAlumnosByCursoId(Integer id);
}
