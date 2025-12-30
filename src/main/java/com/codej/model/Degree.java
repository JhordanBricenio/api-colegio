package com.codej.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "degrees")
@EntityListeners(AuditingEntityListener.class)
public class Degree {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idDegree;

    @Column(name = "course", nullable = false, length = 50)
    private String course;

    @Column(name = "section", nullable = false, length = 50)
    private String section;

    @Column(name = "status", nullable = false)
    private boolean status;

    @CreatedDate
    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "education_level_id", nullable = false)
    private EducationLevel educationLevel;

}
