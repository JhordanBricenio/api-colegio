package com.codej.mapper;

import com.codej.dto.TagDTO;
import com.codej.model.Tag;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagDTO toTagDTO(Tag tag);

    Tag toTagEntity(TagDTO tagDTO);

    List<TagDTO> toTagDTOList(List<Tag> tags);
}
