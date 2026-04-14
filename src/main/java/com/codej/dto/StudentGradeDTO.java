package com.codej.dto;

import com.codej.emuns.Term;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class StudentGradeDTO {

    private UUID idStudentGrade;

    @NotNull
    private boolean status;

    @NotNull
    private String grade;

    @NotNull
    private Term term;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @NotNull
    private String teacherId;

    @NotNull
    private String studentId;

    @NotNull
    private String courseId;

}
