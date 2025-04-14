package com.codej.mapper;

import com.codej.dto.DegreeDTO;
import com.codej.model.Degree;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DegreeMapper {

    DegreeDTO mapIn(Degree degree);

    Degree mapOut(DegreeDTO degreeDTO);

    List<DegreeDTO> mapIn(List<Degree> degrees);
}
