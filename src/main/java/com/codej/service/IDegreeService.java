package com.codej.service;

import com.codej.dto.DegreeCourseDTO;
import com.codej.dto.DegreeWithCourseDTO;
import com.codej.model.Degree;

import java.util.List;

public interface IDegreeService extends ICRUDService<Degree, Integer>{

    public Degree assignCourseToDegree(Integer idDegree, List<DegreeCourseDTO> courses) throws Exception;

    public DegreeWithCourseDTO findDegreeWithCourses(Integer idDegree) throws Exception;
}
