package com.codej.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class KardexDTO {
    private String idRegistration;
    private String registrationStatus;
    private String registrationCreatedAt;
    private String registrationUpdatedAt;

    private String studentId;
    private String studentCode;
    private String studentFullName;
    private String studentDni;
    private String studentPhone;
    private String studentEmail;

    private String parentId;
    private String parentFullName;
    private String parentPhone;
    private String parentDni;
    private String parentRelationship;

    private String degreeId;
    private String degreeCourse;
    private String degreeSection;

    private String educationLevelId;
    private String educationLevelName;

    private String gradesSummary;

    private Integer paymentsCount;
    private BigDecimal paymentsTotal;
}
