package com.codej.controller;

import com.codej.model.Course;
import com.codej.service.ICourseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class CourseRestController {

    private final ICourseService courseService;
    @GetMapping("/courses")
    public List<Course> get(){
        return courseService.findAll();
    }

    @PostMapping("/courses")
    public ResponseEntity<?> save (@RequestBody Course course){
        return courseService.save(course);
    }

    @GetMapping("/courses/{id}")
    public Course findById(@PathVariable Integer id){
        return courseService.findById(id);
    }

    @PutMapping("/courses/{id}")
    public Course update(@RequestBody Course course, @PathVariable Integer id) {
        return courseService.update(course, id);
    }
    @DeleteMapping("/courses/{id}")
    public void delete(@PathVariable Integer id){
        courseService.delete(id);
    }






}
