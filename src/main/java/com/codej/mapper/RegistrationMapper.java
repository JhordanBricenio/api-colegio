package com.codej.mapper;

import com.codej.dto.RegistrationDTO;
import com.codej.model.Registration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RegistrationMapper {

    @Mapping(target = "student.idStudent", source = "idStudent")
    @Mapping(target = "parent.idParent", source = "idParent")
    Registration toRegistrationEntity(RegistrationDTO registrationDTO);

    @Mapping(target = "idStudent", source = "student.idStudent")
    @Mapping(target = "idParent", source = "parent.idParent")
    RegistrationDTO toRegistrationDTO(Registration registration);


    List<RegistrationDTO> toRegistrationDTOList(List<Registration> registrations);
}
