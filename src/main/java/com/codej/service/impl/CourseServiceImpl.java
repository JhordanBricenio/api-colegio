package com.codej.service.impl;

import com.codej.model.Course;
import com.codej.repository.ICourseRepository;
import com.codej.repository.IGenericRepository;
import com.codej.service.ICourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl extends CRUDGenericImpl<Course, Integer> implements ICourseService {

    private final ICourseRepository courseRepository;

    @Override
    protected IGenericRepository<Course, Integer> getRepository() {
        return courseRepository;
    }
}
