package com.codej.mapper;

import com.codej.dto.WorkshopDTO;
import com.codej.model.Workshop;
import com.codej.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface WorkshopMapper {

    @Mapping(target = "idUser", source = "user.idUser")
    WorkshopDTO toWorkshopDTO(Workshop workshop);


    @Mapping(target = "user", source = "idUser", qualifiedByName = "mapUser")
    Workshop toWorkshopEntity(WorkshopDTO workshopDTO);

    List<WorkshopDTO> toWorkshopDTOList(List<Workshop> workshops);

    default UUID map(User user) {
        return user.getIdUser();
    }

    @Named("mapUser")
    default User map(UUID idUser) {
        User user = new User();
        user.setIdUser(idUser);
        return user;
    }






}
