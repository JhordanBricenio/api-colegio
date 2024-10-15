package com.codej.controller.dto;

import com.codej.emuns.StatusAssistance;
import lombok.Data;


@Data
public class AssistanceDetailDTO {

    private Integer studentId;
    private StatusAssistance statusAssistance;
    private String comments;
}
