package com.codej.mapper;

import com.codej.dto.RegistrationDTO;
import com.codej.model.Registration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {StudentMapper.class, ParentMapper.class, DegreeMapper.class, EducationLevelMapper.class})
public interface RegistrationMapper {

    @Mapping(target = "degree", source = "student.degree")
    @Mapping(target = "educationLevel", source = "student.educationLevel")
    RegistrationDTO toRegistrationDTO(Registration registration);

    List<RegistrationDTO> toRegistrationDTOList(List<Registration> registrations);

    @Mapping(target = "student.user.role.idRole", source = "student.user.rolId")
    @Mapping(target = "student.degree.idDegree", source = "student.idDegree")
    @Mapping(target = "student.educationLevel.idEducationLevel", source = "student.idEducationLevel")
    @Mapping(target = "parent.user.role.idRole", source = "parent.user.rolId")
    @Mapping(target = "parent.student.idStudent", source = "parent.idStudent")
    Registration toRegistrationEntity(RegistrationDTO registrationDTO);
}
