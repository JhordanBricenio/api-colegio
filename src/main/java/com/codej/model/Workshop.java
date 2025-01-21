package com.codej.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "workshops")
public class Workshop {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idWorkshop;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "description", length = 65535, nullable = false)
    private String description;

    @Column(name = "hours", nullable = false, length = 3)
    private Integer hours;

    @Column(name = "photo", length = 100)
    private String photo;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

  @JsonIgnoreProperties({"workshops","hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private User user;


  @PrePersist
    public void prePersist() {
        this.creationDate = LocalDateTime.now();
    }
}
