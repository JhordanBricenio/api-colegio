package com.codej.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class CourseRecordDTO {
    private String courseId;
    private String courseName;
    private Map<String, String> termGrades;
    private BigDecimal annualAverage;
    private String status;
}
