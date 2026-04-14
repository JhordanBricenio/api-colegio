package com.codej.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherSubjectAssignmentsDTO {

    private UUID idTeacherSubjectAssignments;

    private boolean status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @NotNull
    private String teacherId;

    private String educationLevelId;

    private String degreeId;

    private String courseId;
}
