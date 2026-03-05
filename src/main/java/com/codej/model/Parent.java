package com.codej.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "parents")
public class Parent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idParent;

    @Column(name = "affinity", length = 50)
    private String affinity;

    @Column(name = "reference_number", length = 50)
    private String referenceNumber;

    @Column(name = "phone", length = 12)
    private String phone;

    @Column(name = "alternate_phone", length = 12)
    private String alternatePhone;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "relation", length = 50)
    private String relation;

    @Column(name = "relationship", length = 50, nullable = false)
    private String relationship;

    @Column(name = "occupation", length = 100, nullable = false)
    private String occupation;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
}
