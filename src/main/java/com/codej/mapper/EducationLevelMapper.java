package com.codej.mapper;

import com.codej.dto.EducationLevelDTO;
import com.codej.model.EducationLevel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EducationLevelMapper {

    @Mapping(target = "idManagement", source = "management.idManagement")
    EducationLevelDTO toEducationLevelDTO(EducationLevel educationLevel);

    @Mapping(target = "management.idManagement", source = "idManagement")
    EducationLevel toEducationLevelEntity(EducationLevelDTO educationLevelDTO);

    List<EducationLevelDTO> mapIn(List<EducationLevel> educationLevels);
}
