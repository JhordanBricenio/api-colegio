package com.codej.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "services")
public class Services {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idServices;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "description", nullable = false, length = 100)
    private String description;

    @Column(name = "image", length = 100)
    private String image;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @JsonIgnoreProperties({"services","hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private User user;

    @PrePersist
    public void prePersist() {
        this.creationDate = LocalDateTime.now();
    }
}
