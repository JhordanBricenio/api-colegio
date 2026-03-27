package com.codej.controller;


import com.codej.dto.TeacherSubjectAssignmentsDTO;
import com.codej.mapper.TeacherSubjectAssignmentsMapper;
import com.codej.model.TeacherSubjectAssignments;
import com.codej.service.ITeacherSubjectAssignmentsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.codej.constants.ApiConstants.ID_IN_PATH;
import static com.codej.constants.ApiConstants.TEACHER_ASSIGNMENTS;


@RestController
@RequestMapping(TEACHER_ASSIGNMENTS)
@CrossOrigin(origins = "http://localhost:4200")
public class TeacherSubjectAssignmentsController {

    private final ITeacherSubjectAssignmentsService teacherSubjectAssignmentsService;
    private final TeacherSubjectAssignmentsMapper teacherSubjectAssignmentsMapper;

    TeacherSubjectAssignmentsController(ITeacherSubjectAssignmentsService teacherSubjectAssignmentsService, TeacherSubjectAssignmentsMapper teacherSubjectAssignmentsMapper) {
        this.teacherSubjectAssignmentsService = teacherSubjectAssignmentsService;
        this.teacherSubjectAssignmentsMapper = teacherSubjectAssignmentsMapper;
    }
    @GetMapping
    public ResponseEntity< List<TeacherSubjectAssignmentsDTO>> findAll() throws Exception {
        teacherSubjectAssignmentsMapper.mapIn(teacherSubjectAssignmentsService.findAll());
        return ResponseEntity.ok(teacherSubjectAssignmentsMapper.mapIn(teacherSubjectAssignmentsService.findAll()));
    }

    @PostMapping
    public ResponseEntity<TeacherSubjectAssignmentsDTO> save(@Valid @RequestBody TeacherSubjectAssignmentsDTO teacherSubjectAssignmentsDTO) throws Exception {
        TeacherSubjectAssignments teacherSubjectAssignments= teacherSubjectAssignmentsMapper.mapOut(teacherSubjectAssignmentsDTO);
        TeacherSubjectAssignments savedTeacherSubjectAssignments = teacherSubjectAssignmentsService.save(teacherSubjectAssignments);
        return ResponseEntity.status(HttpStatus.CREATED).body(teacherSubjectAssignmentsMapper.mapIn(savedTeacherSubjectAssignments));
    }
    @GetMapping(ID_IN_PATH)
    public ResponseEntity<TeacherSubjectAssignmentsDTO> findById(@PathVariable UUID id) throws Exception {
        return ResponseEntity.ok(teacherSubjectAssignmentsMapper.mapIn(teacherSubjectAssignmentsService.findById(id)));
    }


    @PatchMapping(ID_IN_PATH)
    public ResponseEntity<TeacherSubjectAssignmentsDTO> update(@Valid @RequestBody TeacherSubjectAssignmentsDTO teacherSubjectAssignmentsDTO,@PathVariable UUID id) throws Exception {
        TeacherSubjectAssignments teacherSubjectAssignments = teacherSubjectAssignmentsMapper.mapOut(teacherSubjectAssignmentsDTO);
        TeacherSubjectAssignments updatedTeacherSubjectAssignments = teacherSubjectAssignmentsService.update(teacherSubjectAssignments, id);
        return ResponseEntity.ok(teacherSubjectAssignmentsMapper.mapIn(updatedTeacherSubjectAssignments));
    }
    @DeleteMapping(ID_IN_PATH)
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws Exception {
        teacherSubjectAssignmentsService.delete(id);
        return  ResponseEntity.noContent().build();
    }


}
