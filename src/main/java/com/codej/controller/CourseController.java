package com.codej.controller;


import com.codej.dto.CourseDTO;
import com.codej.mapper.CourseMapper;
import com.codej.model.Course;
import com.codej.service.ICourseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.codej.constants.ApiConstants.*;

@RestController
@RequestMapping(COURSE_BASE)
@AllArgsConstructor
public class CourseController {

    private final ICourseService courseService;
    private final CourseMapper courseMapper;


   @GetMapping
    public ResponseEntity< List<CourseDTO>> findAll() throws Exception {
        return ResponseEntity.ok(courseMapper.mapIn(courseService.findAll()));
    }

    @GetMapping("/paged/{page}")
    public Page<CourseDTO> findAllPaged(@PathVariable Integer page, @RequestParam(required = false) String educationLevelId) throws Exception {
        Pageable pageable = PageRequest.of(page, 8);
        UUID eduId = (educationLevelId == null) ? null : UUID.fromString(educationLevelId);
        Page<Course> coursePage = courseService.findAllPaged(pageable, eduId);
        return coursePage.map(courseMapper::mapIn);
    }

    @PostMapping
    public ResponseEntity<CourseDTO> save(@Valid @RequestBody CourseDTO courseDTO) throws Exception {
        Course course= courseMapper.mapOut(courseDTO);
        Course savedCourse = courseService.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseMapper.mapIn(savedCourse));
    }
    @GetMapping(ID_IN_PATH)
    public ResponseEntity<CourseDTO> findById(@PathVariable UUID id) throws Exception {
        return ResponseEntity.ok(courseMapper.mapIn(courseService.findById(id)));
    }

    @PutMapping(ID_IN_PATH)
    public ResponseEntity<CourseDTO> update(@Valid @RequestBody CourseDTO courseDTO,@PathVariable UUID id)
            throws Exception {
        Course course = courseMapper.mapOut(courseDTO);
        Course updatedCourse = courseService.update(course, id);
        return ResponseEntity.ok(courseMapper.mapIn(updatedCourse));
    }
    @DeleteMapping(ID_IN_PATH)
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws Exception {
        courseService.delete(id);
        return  ResponseEntity.noContent().build();
    }



}
