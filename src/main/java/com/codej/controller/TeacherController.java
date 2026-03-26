package com.codej.controller;


import com.codej.dto.DniRequest;
import com.codej.dto.TeacherDTO;
import com.codej.mapper.TeacherMapper;
import com.codej.model.Teacher;
import com.codej.service.ITeacherService;
import com.codej.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.codej.constants.ApiConstants.ID_IN_PATH;
import static com.codej.constants.ApiConstants.TEACHER_BASE;


@RestController
@RequestMapping(TEACHER_BASE)
@CrossOrigin(origins = "http://localhost:4200")
public class TeacherController {

    private final ITeacherService teacherService;
    private final IUserService userService;
    private final TeacherMapper teacherMapper;

    TeacherController(ITeacherService teacherService, TeacherMapper teacherMapper, IUserService userService) {
        this.userService = userService;
        this.teacherService = teacherService;
        this.teacherMapper = teacherMapper;
    }
    @GetMapping
    public ResponseEntity< List<TeacherDTO>> findAll() throws Exception {
        teacherMapper.toTeacherDTOList(teacherService.findAll());
        return ResponseEntity.ok(teacherMapper.toTeacherDTOList(teacherService.findAll()));
    }

    @GetMapping("/paged/{page}")
    public Page< TeacherDTO> findAllPaged(@PathVariable Integer page) throws Exception {
        Pageable pageable = PageRequest.of(page, 8);
        Page<Teacher> teacherPage = teacherService.findAllPaged(pageable);
        return teacherPage.map(teacherMapper::toTeacherDTO);
    }

    @PostMapping
    public ResponseEntity<TeacherDTO> save(@Valid @RequestBody TeacherDTO teacherDTO) throws Exception {
        Teacher teacher= teacherMapper.toTeacherEntity(teacherDTO);
        teacher.getUser().setPassword(teacherDTO.getUser().getDni());
        Teacher savedTeacher = teacherService.saveTeacher(teacher);
        return ResponseEntity.status(HttpStatus.CREATED).body(teacherMapper.toTeacherDTO(savedTeacher));
    }
    @GetMapping(ID_IN_PATH)
    public ResponseEntity<TeacherDTO> findById(@PathVariable UUID id) throws Exception {
        return ResponseEntity.ok(teacherMapper.toTeacherDTO(teacherService.findById(id)));
    }
    @PostMapping("/dni")
    public ResponseEntity<TeacherDTO> findByDni(@RequestBody DniRequest dniRequest) throws Exception {
        return ResponseEntity.ok(teacherMapper.toTeacherDTO(teacherService.findByDni(dniRequest.getDni())));
    }

    @PatchMapping(ID_IN_PATH)
    public ResponseEntity<TeacherDTO> update(@Valid @RequestBody TeacherDTO teacherDTO,@PathVariable UUID id) throws Exception {
        Teacher teacher = teacherMapper.toTeacherEntity(teacherDTO);
        teacher.getUser().setPassword(teacherDTO.getUser().getDni());
        Teacher updatedTeacher = teacherService.update(teacher, id);
        return ResponseEntity.ok(teacherMapper.toTeacherDTO(updatedTeacher));
    }
    @DeleteMapping(ID_IN_PATH)
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws Exception {
        teacherService.delete(id);
        return  ResponseEntity.noContent().build();
    }

    @GetMapping("/dni/{numero}")
    public ResponseEntity<String> searchByDni(@PathVariable String numero) throws Exception {
       return userService.searchByDni(numero);
    }



}
