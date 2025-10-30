package com.codej.mapper;

import com.codej.dto.ManagementDTO;
import com.codej.model.Management;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ManagementMapper {

    ManagementDTO mapIn(Management management);

    Management mapOut(ManagementDTO managementDTO);

    List<ManagementDTO> mapIn(List<Management> managements);
}
