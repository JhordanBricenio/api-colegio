package com.codej.model;

import com.codej.emuns.StatusAttendance;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "attendances_student", uniqueConstraints = {
        @UniqueConstraint(name = "uk_student_date_session", columnNames = {"student_id", "attendance_date", "session"})
})
@EntityListeners(AuditingEntityListener.class)
public class AttendanceStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_attendance")
    private UUID idAttendance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(name = "attendance_date", nullable = false)
    private LocalDate attendanceDate;

    @Column(name = "session", length = 50)
    private String session;

    @Column(name = "status", length = 20, nullable = false)
    private StatusAttendance status;

    @Column(name = "minutes_late")
    private Integer minutesLate;

    @Column(name = "justification", columnDefinition = "TEXT")
    private String justification;

    @Column(name = "source", length = 50)
    private String source;

    // Nueva relación opcional a Course (para registros por curso, p.ej. secundaria)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    // Nueva relación opcional a Degree (para registros por grado/section, p.ej. primaria/inicial)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "degree_id")
    private Degree degree;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    private UUID createdBy;

}
