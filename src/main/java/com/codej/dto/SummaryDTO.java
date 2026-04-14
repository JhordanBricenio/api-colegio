package com.codej.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class SummaryDTO {
    private BigDecimal gpa;
    private int approvedCourses;
    private int failedCourses;
    private int inProgressCourses;
}
