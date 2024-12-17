package com.codej.service.impl;

import com.codej.model.Course;
import com.codej.repository.ICourseRepository;
import com.codej.service.ICourseService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements ICourseService {

    private ICourseRepository courseRepository;


    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public Course findById(Integer id) {
        return courseRepository.findById(id).orElse(null);
    }

    @Override
    public ResponseEntity<?> save(Course course) {

        Map<String, Object> response = new HashMap<>();
        Course cursoNew = null;
        try {
            cursoNew = courseRepository.save(course);
        }catch (Exception e){
            response.put("mensaje", "Error al crear el curso");
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        response.put("mensaje", "El curso ha sido creado con éxito");
        response.put("proyecto", cursoNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @Override
    public Course update(Course course, Integer id) {
        Course cursoUpdate = findById(course.getId());
        cursoUpdate.setName(course.getName());
        cursoUpdate.setCredits(course.getCredits());
        cursoUpdate.setHours(course.getHours());
        cursoUpdate.setDescription(course.getDescription());
        cursoUpdate.setSemester(course.getSemester());
        return courseRepository.save(course);
    }

    @Override
    public void delete(Integer id) {
        courseRepository.deleteById(id);
    }

    @Override
    public Page<Course> findAll(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }
}
