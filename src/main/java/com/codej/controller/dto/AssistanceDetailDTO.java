package com.codej.controller.dto;

import com.codej.emuns.StatusAssistance;
import lombok.Data;

import java.util.UUID;


@Data
public class AssistanceDetailDTO {

    private UUID studentId;
    private StatusAssistance statusAssistance;
    private String comments;
}
