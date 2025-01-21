package com.codej.mapper;

import com.codej.dto.PostDTO;
import com.codej.model.Post;
import com.codej.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "idUser", source = "user")
   PostDTO toPostDTO(Post post);

    @Mapping(target = "user" , source = "idUser")
    Post toPostEntity(PostDTO postDTO);

    List<PostDTO> toPostDTOList(List<Post> posts);



    default User map(UUID idUser) {
        if (idUser == null) {
            return null;
        }
        User user = new User();
        user.setIdUser(idUser);
        return user;
    }

    default UUID map(User user) {
        if (user == null) {
            return null;
        }
        return user.getIdUser();
    }


}
