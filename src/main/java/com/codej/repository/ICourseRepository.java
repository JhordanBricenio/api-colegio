package com.codej.repository;

import com.codej.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ICourseRepository  extends IGenericRepository<Course, UUID> {

    Page<Course> findByEducationLevel_IdEducationLevel(UUID educationLevelId, Pageable pageable);

}
