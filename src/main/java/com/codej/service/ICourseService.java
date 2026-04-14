package com.codej.service;

import com.codej.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ICourseService extends ICRUDService<Course, UUID> {
    Page<Course> findAllPaged(Pageable pageable, UUID educationLevelId) throws Exception;
}
