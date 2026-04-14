package com.codej.service.impl;

import com.codej.model.Course;
import com.codej.repository.ICourseRepository;
import com.codej.repository.IGenericRepository;
import com.codej.service.ICourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl extends CRUDGenericImpl<Course, UUID> implements ICourseService {

    private final ICourseRepository courseRepository;

    @Override
    protected IGenericRepository<Course, UUID> getRepository() {
        return courseRepository;
    }

    @Override
    public Page<Course> findAllPaged(Pageable pageable, UUID educationLevelId) throws Exception {
        if (educationLevelId == null) {
            return courseRepository.findAll(pageable);
        }
        return courseRepository.findByEducationLevel_IdEducationLevel(educationLevelId, pageable);
    }
}
