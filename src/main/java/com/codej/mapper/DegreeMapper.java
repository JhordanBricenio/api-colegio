package com.codej.mapper;

import com.codej.dto.DegreeDTO;
import com.codej.model.Degree;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DegreeMapper {

    @Mapping(target = "idEducationLevel", source = "educationLevel.idEducationLevel")
    DegreeDTO mapIn(Degree degree);

    @Mapping(target = "educationLevel.idEducationLevel", source = "idEducationLevel")
    Degree mapOut(DegreeDTO degreeDTO);

    List<DegreeDTO> mapIn(List<Degree> degrees);
}
