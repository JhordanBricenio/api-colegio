package com.codej.mapper;

import com.codej.dto.PostDTO;
import com.codej.model.Post;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

   /* PostDTO toPostDTO(Post post);

    Post toPostEntity(PostDTO postDTO);

    List<PostDTO> toPostDTOList(List<Post> posts);*/

/*    @Mapping(target = "posts", ignore = true) // Evitar bucle infinito
    TagDTO toTagDTO(Tag tag);

    @Mapping(target = "posts", ignore = true) // Evitar bucle infinito
    Tag toTag(TagDTO tagDTO);*/
}
