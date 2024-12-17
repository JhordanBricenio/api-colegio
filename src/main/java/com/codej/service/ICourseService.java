package com.codej.service;

import com.codej.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICourseService {
    public List<Course> findAll();
    public Course findById(Integer id);
    public ResponseEntity<?> save (Course course);

    public Course update(Course course, Integer id);


    public void delete(Integer id);
    public Page<Course> findAll(Pageable pageable);
}
