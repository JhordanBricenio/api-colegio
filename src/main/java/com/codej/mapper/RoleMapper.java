package com.codej.mapper;

import com.codej.dto.RoleDTO;
import com.codej.model.Role;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleDTO mapIn(Role role);

    Role mapOut(RoleDTO roleDTO);

    List<RoleDTO> mapIn(List<Role> roles);
}
