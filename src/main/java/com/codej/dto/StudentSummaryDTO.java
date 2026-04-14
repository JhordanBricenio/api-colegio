package com.codej.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentSummaryDTO {
    private String id;
    private String code;
    private String fullName;

    private String studentDni;
    private String studentPhone;
    private String studentEmail;

    private String degreeId;
    private String degreeCourse;
    private String degreeSection;

    private String educationLevelId;
    private String educationLevelName;

    private String parentId;
    private String parentFullName;
    private String parentPhone;
    private String parentDni;
    private String parentRelationship;
}
