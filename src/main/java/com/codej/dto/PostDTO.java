package com.codej.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PostDTO {

    private UUID id;
    private String title;
    private String content;
    private String image;
    private Date date;
    private UserDTO user;
    private List<TagDTO> tags;
}
