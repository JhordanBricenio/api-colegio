package com.codej.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * DTO that returns all registration data enriched with the student's full name,
 * parent's full name, degree information and education level.
 */
@Getter
@Setter
public class RegistrationSummaryDTO {

    private UUID idRegistration;

    private boolean status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // Student info
    private String studentName;

    private String studentLastName;

    private String studentFullName;

    private String studentCode;

    // Parent info
    private String parentName;

    private String parentLastName;

    private String parentFullName;

    private String parentRelationship;

    private String parentOccupation;

    // Degree info
    private String degreeCourse;

    private String degreeSection;

    // Education level info
    private String educationLevelName;

    /**
     * Constructor used by the JPQL constructor expression in IRegistrationRepository.
     */
    public RegistrationSummaryDTO(UUID idRegistration, boolean status,
                                  LocalDateTime createdAt, LocalDateTime updatedAt,
                                  String studentName, String studentLastName, String studentCode,
                                  String parentName, String parentLastName,
                                  String parentRelationship, String parentOccupation,
                                  String degreeCourse, String degreeSection,
                                  String educationLevelName) {
        this.idRegistration = idRegistration;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.studentName = studentName;
        this.studentLastName = studentLastName;
        this.studentFullName = Objects.toString(studentName, "") + " " + Objects.toString(studentLastName, "");
        this.studentCode = studentCode;
        this.parentName = parentName;
        this.parentLastName = parentLastName;
        this.parentFullName = Objects.toString(parentName, "") + " " + Objects.toString(parentLastName, "");
        this.parentRelationship = parentRelationship;
        this.parentOccupation = parentOccupation;
        this.degreeCourse = degreeCourse;
        this.degreeSection = degreeSection;
        this.educationLevelName = educationLevelName;
    }
}
