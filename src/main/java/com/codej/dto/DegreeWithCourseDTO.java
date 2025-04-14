package com.codej.dto;

import com.codej.model.Course;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class DegreeWithCourseDTO {

    private Integer idDegree;
    private String name;
    private List<CourseDTO> courses;

    public DegreeWithCourseDTO(Integer idDegree, String name, List<Course> courses) {
        this.idDegree = idDegree;
        this.name = name;
        this.courses = courses.stream()
                .map(course -> new CourseDTO(course.getIdCourse(), course.getName(), course.getDescription()))
                .collect(Collectors.toList());
    }
}
