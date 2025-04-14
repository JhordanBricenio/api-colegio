package com.codej.mapper;

import com.codej.dto.CourseDTO;
import com.codej.model.Course;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    CourseDTO mapIn(Course course);

    Course mapOut(CourseDTO courseDTO);

    List<CourseDTO> mapIn(List<Course> courses);
}
