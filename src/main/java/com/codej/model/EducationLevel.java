package com.codej.model;

import com.codej.emuns.Shift;
import com.codej.emuns.EducationLevelType;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "level_type", nullable = false)
    private EducationLevelType levelType;

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

    @PrePersist
    public void prePersist(){
        if (this.levelType == null) {
            if (this.name != null) {
                String n = this.name.trim().toUpperCase();
                if (n.contains("INICIAL")) this.levelType = EducationLevelType.INITIAL;
                else if (n.contains("PRIMARIA")) this.levelType = EducationLevelType.PRIMARY;
                else if (n.contains("SECUNDARIA")) this.levelType = EducationLevelType.SECONDARY;
                else this.levelType = EducationLevelType.OTHER;
            } else {
                this.levelType = EducationLevelType.OTHER;
            }
        }
    }

}
