package com.codej.controller.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AssistanceRequestDTO {
    private Date date;
    private Integer teacherId;
    private List<AssistanceDetailDTO> assistanceDetailDTOS;
}
