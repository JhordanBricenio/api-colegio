package com.codej.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public class ParentDTO {

    private UUID idParent;

    private String relationship;

    private String occupation;

    private UserDTO user;

    private String idStudent;
}
