package com.codej.controller;

import com.codej.models.Course;
import com.codej.services.ICourseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class CourseRestController {

    private final ICourseService courseService;
    @GetMapping("/courses")
    public List<Course> get(){
        return courseService.findAll();
    }

}
