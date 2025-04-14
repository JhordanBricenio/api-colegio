package com.codej.controller;


import com.codej.dto.CourseDTO;
import com.codej.mapper.CourseMapper;
import com.codej.model.Course;
import com.codej.service.ICourseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.codej.constants.ApiConstants.*;

@RestController
@RequestMapping(COURSE_BASE)
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class CourseController {

    private final ICourseService courseService;
    private final CourseMapper courseMapper;


   @GetMapping
    public ResponseEntity< List<CourseDTO>> findAll() throws Exception {
        return ResponseEntity.ok(courseMapper.mapIn(courseService.findAll()));
    }
    @PostMapping
    public ResponseEntity<CourseDTO> save(@Valid @RequestBody CourseDTO courseDTO) throws Exception {
        Course course= courseMapper.mapOut(courseDTO);
        Course savedCourse = courseService.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseMapper.mapIn(savedCourse));
    }
    @GetMapping(ID_IN_PATH)
    public ResponseEntity<CourseDTO> findById(@PathVariable Integer id) throws Exception {
        return ResponseEntity.ok(courseMapper.mapIn(courseService.findById(id)));
    }

    @PutMapping(ID_IN_PATH)
    public ResponseEntity<CourseDTO> update(@Valid @RequestBody CourseDTO courseDTO,@PathVariable Integer id)
            throws Exception {
        Course course = courseMapper.mapOut(courseDTO);
        Course updatedCourse = courseService.update(course, id);
        return ResponseEntity.ok(courseMapper.mapIn(updatedCourse));
    }
    @DeleteMapping(ID_IN_PATH)
    public ResponseEntity<Void> delete(@PathVariable Integer id) throws Exception {
        courseService.delete(id);
        return  ResponseEntity.noContent().build();
    }



}
