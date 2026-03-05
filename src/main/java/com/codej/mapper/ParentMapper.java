package com.codej.mapper;

import com.codej.dto.ParentDTO;
import com.codej.model.Parent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParentMapper {

    @Mapping(target = "user.role.idRole", source = "user.rolId")
    @Mapping(target = "student.idStudent", source = "idStudent")
    Parent toParentEntity(ParentDTO parentDTO);

    @Mapping(target = "user.rolId", source = "user.role.idRole")
    @Mapping(target = "idStudent", source = "student.idStudent")
    ParentDTO toParentDTO(Parent parent);


    List<ParentDTO> toParentDTOList(List<Parent> parents);
}
