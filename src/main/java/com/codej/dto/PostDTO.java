package com.codej.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

    private UUID idPost;

    @NotNull
    private String title;
    @NotNull
    private String content;
    @NotNull
    private String image;
    @NotNull
    private LocalDateTime date;

    private UUID idUser;

    private List<TagDTO> tags;
}
