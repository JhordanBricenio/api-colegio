package com.codej.controller;


import com.codej.dto.CourseRecordDTO;
import com.codej.dto.StudentGradeDTO;
import com.codej.mapper.StudentGradeMapper;
import com.codej.model.StudentGrade;
import com.codej.service.IStudentGradeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.codej.constants.ApiConstants.ID_IN_PATH;
import static com.codej.constants.ApiConstants.STUDENT_GRADE;


@RestController
@RequestMapping(STUDENT_GRADE)
public class StudentGradeController {

    private final IStudentGradeService studentGradeService;
    private final StudentGradeMapper studentGradeMapper;

    public StudentGradeController(IStudentGradeService studentGradeService, StudentGradeMapper studentGradeMapper) {
        this.studentGradeService = studentGradeService;
        this.studentGradeMapper = studentGradeMapper;
    }
    @GetMapping
    public ResponseEntity< List<StudentGradeDTO>> findAll() throws Exception {
        return ResponseEntity.ok(studentGradeMapper.mapIn(studentGradeService.findAll()));
    }

    @PostMapping
    public ResponseEntity<StudentGradeDTO> save(@Valid @RequestBody StudentGradeDTO studentGradeDTO) throws Exception {
        StudentGrade studentGrade= studentGradeMapper.mapOut(studentGradeDTO);
        StudentGrade savedStudentGrade = studentGradeService.save(studentGrade);
        return ResponseEntity.status(HttpStatus.CREATED).body(studentGradeMapper.mapIn(savedStudentGrade));
    }
    @GetMapping(ID_IN_PATH)
    public ResponseEntity<StudentGradeDTO> findById(@PathVariable UUID id) throws Exception {
        return ResponseEntity.ok(studentGradeMapper.mapIn(studentGradeService.findById(id)));
    }

    // Obtener todas las notas de un estudiante por su id, incluyendo courseName, bimestres, promedio y estado
    @GetMapping("/student/{id}")
    public ResponseEntity<List<CourseRecordDTO>> findByGradesStudentId(@PathVariable UUID id) throws Exception {
        return ResponseEntity.ok(studentGradeService.getCourseRecordsByStudentId(id));
    }

    @PatchMapping(ID_IN_PATH)
    public ResponseEntity<StudentGradeDTO> update(@Valid @RequestBody StudentGradeDTO studentGradeDTO,@PathVariable UUID id) throws Exception {
        StudentGrade studentGrade = studentGradeMapper.mapOut(studentGradeDTO);
        StudentGrade updatedStudentGrade = studentGradeService.update(studentGrade, id);
        return ResponseEntity.ok(studentGradeMapper.mapIn(updatedStudentGrade));
    }
    @DeleteMapping(ID_IN_PATH)
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws Exception {
        studentGradeService.delete(id);
        return  ResponseEntity.noContent().build();
    }

}
