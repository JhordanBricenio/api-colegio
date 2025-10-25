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
@Table(name = "settings")
@EntityListeners(AuditingEntityListener.class)
public class Setting {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idSetting;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "logo", length = 300)
    private String logo;

    @Column(name = "address", length = 100, nullable = false)
    private String address;

    @Column(name = "phone", length = 100, nullable = false)
    private String phone;

    @Column(name = "email", length = 100)
    private String email;

    @CreatedDate
    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;





}
