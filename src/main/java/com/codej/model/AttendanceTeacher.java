package com.codej.model;

import com.codej.emuns.StatusAttendance;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "attendances_teacher", uniqueConstraints = {
        @UniqueConstraint(name = "uk_teacher_date_session", columnNames = {"teacher_id", "attendance_date", "session"})
})
public class AttendanceTeacher {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_attendance")
    private UUID idAttendance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

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

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    private UUID createdBy;

}
