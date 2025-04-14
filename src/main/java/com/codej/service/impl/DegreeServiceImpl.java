package com.codej.service.impl;

import com.codej.dto.DegreeCourseDTO;
import com.codej.dto.DegreeWithCourseDTO;
import com.codej.exceptions.ResourceNotFoundException;
import com.codej.model.Course;
import com.codej.model.Degree;
import com.codej.repository.IDegreeRepository;
import com.codej.repository.IGenericRepository;
import com.codej.service.IDegreeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.codej.constants.ErrorMessageConstants.NOT_RESULTS_FOUND_FOR_WITH_ID;

@Service
@RequiredArgsConstructor
public class DegreeServiceImpl extends CRUDGenericImpl<Degree, Integer> implements IDegreeService {

    private final IDegreeRepository degreeRepository;

    @Override
    protected IGenericRepository<Degree, Integer> getRepository() {
        return degreeRepository;
    }

    @Override
    public Degree assignCourseToDegree(Integer idDegree, List<DegreeCourseDTO> courses) throws Exception {
        Degree degree = degreeRepository.findById(idDegree).orElseThrow(
                ()-> new ResourceNotFoundException(NOT_RESULTS_FOUND_FOR_WITH_ID+ idDegree));

        for (DegreeCourseDTO courseDTO : courses) {
            Course course = new Course();
            course.setIdCourse(courseDTO.getCourseId());
            degree.getCourses().add(course);
        }
        return degreeRepository.save(degree);
    }

    @Override
    public DegreeWithCourseDTO findDegreeWithCourses(Integer idDegree) throws Exception {
        Degree degree = degreeRepository.findById(idDegree).orElseThrow(
                ()-> new ResourceNotFoundException(NOT_RESULTS_FOUND_FOR_WITH_ID+ idDegree));

        List<Course> courses = new ArrayList<>(degree.getCourses());

        return new DegreeWithCourseDTO(degree.getIdDegree(), degree.getName(),courses);
    }


}
