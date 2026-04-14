package com.codej.dto.attendance;

import com.codej.emuns.StatusAttendance;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class AttendanceTeacherDTO {
    private UUID idAttendance;
    private UUID teacherId;
    private String attendanceDate;
    private String session;
    private StatusAttendance status;
    private Integer minutesLate;
    private String justification;
    private String source;
}
