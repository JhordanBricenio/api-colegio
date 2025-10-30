package com.codej.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "managements")
@EntityListeners(AuditingEntityListener.class)
public class Management {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idManagement;

    @Column(name = "name", length = 50, nullable = false, unique = true)
    private String name;

    @Column(name = "status", nullable = false)
    private boolean status;

    @CreatedDate
    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
