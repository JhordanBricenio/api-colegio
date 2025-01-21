package com.codej.mapper;

import com.codej.dto.ServiceDTO;
import com.codej.model.Services;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ServiceMapper {


    @Mapping(target = "user", source = "user")
    ServiceDTO toServicesDTO(Services services);

    @Mapping(target = "user", source = "user")
    Services toServicesEntity(ServiceDTO serviceDTO);

    List<ServiceDTO> toServicesDTOList(List<Services> services);
}
