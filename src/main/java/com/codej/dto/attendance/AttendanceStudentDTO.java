package com.codej.dto.attendance;

import com.codej.emuns.StatusAttendance;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class AttendanceStudentDTO {
    private UUID idAttendance;
    private UUID studentId;
    private String attendanceDate;
    private String session;
    private StatusAttendance status;
    private Integer minutesLate;
    private String justification;
    private String source;

    // Opcionales: scope del registro
    private String courseId;
    private String degreeId;
}
