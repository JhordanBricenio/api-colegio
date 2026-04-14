package com.codej.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class KardexProfessionalDTO {
    private StudentSummaryDTO student;
    private List<YearRecordDTO> records;
}
