package com.codej.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "teacher_subject_assignments")
@EntityListeners(AuditingEntityListener.class)
public class TeacherSubjectAssignments {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idTeacherSubjectAssignments;

    @Column(name = "status", nullable = false)
    private boolean status;

    @CreatedDate
    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "educationLevel_id", nullable = false)
    private EducationLevel educationLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "degree_id", nullable = false)
    private Degree degree;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

}
