package com.codej.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AuthResponseDTO {
    private String accessToken;
    private LocalDateTime expiresAt;
    private UserSummaryDTO user;
}
