package com.codej.mapper;

import com.codej.dto.CourseDTO;
import com.codej.model.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    @Mapping(target = "educationLevelId", source = "educationLevel.idEducationLevel")
    CourseDTO mapIn(Course course);

    @Mapping(target = "educationLevel.idEducationLevel", source = "educationLevelId")
    Course mapOut(CourseDTO courseDTO);

    List<CourseDTO> mapIn(List<Course> courses);
}
