package com.codej.model;

import com.codej.emuns.Shift;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "education_levels")
@EntityListeners(AuditingEntityListener.class)
public class EducationLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idEducationLevel;

    @Column(name = "name", length = 50, nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "shift", nullable = false)
    private Shift shift;

    @Column(name = "status", nullable = false)
    private boolean status;

    @CreatedDate
    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "management_id", nullable = false)
    private Management management;

}
