package com.codej.mapper;

import com.codej.dto.UserDTO;
import com.codej.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    // UserDTO to User
    @Mapping(target = "sex", source = "sex")
    User toUserEntity(UserDTO userDTO);
    // User to UserDTO
    @Mapping(target = "sex", source = "sex")
    UserDTO toUserDTO(User user);


    List<UserDTO> toUserDTOList(List<User> users);
}
