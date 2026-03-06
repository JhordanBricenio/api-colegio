package com.codej.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO used for creating and updating a Registration.
 * References student and parent by their UUIDs.
 */
@Getter
@Setter
public class RegistrationDTO {

    private UUID idRegistration;

    private boolean status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String idStudent;

    private String idParent;
}
