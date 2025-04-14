package com.codej.controller;


import com.codej.dto.DegreeCourseDTO;
import com.codej.dto.DegreeDTO;
import com.codej.dto.DegreeWithCourseDTO;
import com.codej.mapper.DegreeMapper;
import com.codej.model.Degree;
import com.codej.service.IDegreeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.codej.constants.ApiConstants.*;

@RestController
@RequestMapping(DEGREE_BASE)
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class DegreeController {

    private final IDegreeService degreeService;
    private final DegreeMapper degreeMapper;


     @GetMapping
    public ResponseEntity< List<DegreeDTO>> findAll() throws Exception {
        return ResponseEntity.ok(degreeMapper.mapIn(degreeService.findAll()));
    }

    @PostMapping
    public ResponseEntity<DegreeDTO> save(@Valid @RequestBody DegreeDTO degreeDTO) throws Exception {
        Degree degree= degreeMapper.mapOut(degreeDTO);
        Degree savedDegree = degreeService.save(degree);
        return ResponseEntity.status(HttpStatus.CREATED).body(degreeMapper.mapIn(savedDegree));
    }

    @PostMapping(ASSIGN_COURSE_TO_DEGREE)
    public ResponseEntity<?> assignCourseToDegree(@PathVariable Integer idDegree,
                                                  @RequestBody List<DegreeCourseDTO> degreeCourseDTO) throws Exception {
        degreeService.assignCourseToDegree(idDegree, degreeCourseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(ID_IN_PATH)
    public ResponseEntity<DegreeDTO> findById(@PathVariable Integer id) throws Exception {
        return ResponseEntity.ok(degreeMapper.mapIn(degreeService.findById(id)));
    }

    @GetMapping("/withCourses/{idDegree}")
    public ResponseEntity<?> findDegreeWithCourses(@PathVariable Integer idDegree) throws Exception {
        DegreeWithCourseDTO degreeWithCourseDTO = degreeService.findDegreeWithCourses(idDegree);
        return ResponseEntity.ok(degreeWithCourseDTO);
    }

    @PutMapping(ID_IN_PATH)
    public ResponseEntity<DegreeDTO> update(@Valid @RequestBody DegreeDTO degreeDTO,@PathVariable Integer id)
            throws Exception {
        Degree degree = degreeMapper.mapOut(degreeDTO);
        Degree updatedDegree = degreeService.update(degree, id);
        return ResponseEntity.ok(degreeMapper.mapIn(updatedDegree));
    }
    @DeleteMapping(ID_IN_PATH)
    public ResponseEntity<Void> delete(@PathVariable Integer id) throws Exception {
        degreeService.delete(id);
        return  ResponseEntity.noContent().build();
    }



}
