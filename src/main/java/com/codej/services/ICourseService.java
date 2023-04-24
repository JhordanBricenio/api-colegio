package com.codej.services;

import com.codej.models.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICourseService {
    public List<Course> findAll();
    public Course findById(Integer id);
    public Course save (Course course);
    public void delete(Integer id);
    public Page<Course> findAll(Pageable pageable);
}
