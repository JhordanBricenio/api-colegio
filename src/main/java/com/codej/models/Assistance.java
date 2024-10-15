package com.codej.models;

import com.codej.emuns.StatusAssistance;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "assistance")
public class Assistance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date date;

    @Enumerated(EnumType.STRING)
    private StatusAssistance statusAssistance;

    private String comments;

    @JsonIgnoreProperties({"assistance","hibernateLazyInitializer", "handler"})
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    public void prePersist(){
        this.date = new Date();
    }


}
