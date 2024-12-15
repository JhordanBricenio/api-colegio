package com.codej.repositories;

import com.codej.controller.dto.RegistrationDTO;
import com.codej.controller.dto.UserDTO;
import com.codej.models.Registration;
import com.codej.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IRegistrationRepository extends JpaRepository<Registration, Long> {

    @Query("SELECT m FROM Registration m JOIN m.user u WHERE u.dni = :dni")
    Optional<Registration> findRegistrationByDnii(@Param("dni") String dni);

    @Query("SELECT new com.codej.controller.dto.RegistrationDTO(m.id, m.fechaMatricula,m.fechaInicio, m.fechaFin," +
            " m.statusRegistration," +
            "m.statusPayment, m.costo, m.descuento, m.periodoAcademico, m.turno, m.seccion, m.jornada," +
            " new com.codej.controller.dto.UserDTO(u.id, u.name, u.lastname, u.dni, u.email)) " +
            "FROM Registration m JOIN m.user u WHERE u.dni = :dni")
    Optional<RegistrationDTO> findRegistrationByDni(@Param("dni") String dni);

    @Query("SELECT new com.codej.controller.dto.RegistrationDTO(m.id, m.fechaMatricula, m.fechaInicio, m.fechaFin, " +
            "m.statusRegistration, m.statusPayment, m.costo, m.descuento, m.periodoAcademico, m.turno, m.seccion, m.jornada, " +
            "new com.codej.controller.dto.UserDTO(u.id, u.name, u.lastname, u.dni, u.email)) " +
            "FROM Registration m JOIN m.user u")
    List<RegistrationDTO> findAllRegistrations();

    @Query("SELECT new com.codej.controller.dto.UserDTO(u.id, u.name, u.lastname, u.dni, u.email) " +
            "FROM Registration m JOIN m.user u WHERE m.degree.id = :degreeId")
    List<UserDTO> findUsersByDegreeId(@Param("degreeId") Integer degreeId);


    @Query("SELECT new com.codej.controller.dto.UserDTO(u.id, u.name, u.lastname, u.dni, u.email) " +
            "FROM Registration m " +
            "JOIN m.degree g " +
            "JOIN g.courses c " +
            "JOIN m.user u " +  // Es necesario un JOIN para acceder a la entidad Usuario
            "WHERE c.id = :courseId")
    List<UserDTO> findAlumnosByCursoId(@Param("courseId") Integer courseId);




}
