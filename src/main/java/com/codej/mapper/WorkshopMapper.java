package com.codej.mapper;

import com.codej.dto.WorkshopDTO;
import com.codej.model.Workshop;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WorkshopMapper {


    WorkshopDTO toWorkshopDTO(Workshop workshop);



    Workshop toWorkshopEntity(WorkshopDTO workshopDTO);

    List<WorkshopDTO> toWorkshopDTOList(List<Workshop> workshops);



}
