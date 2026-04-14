package com.codej.dto.attendance;

import com.codej.emuns.StatusAttendance;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BulkAttendanceStudentRequestDTO {
    private String date; // yyyy-MM-dd
    private String session;
    private List<BulkRecord> records;
    private String conflictStrategy; // skip | update | fail

    // Opcional: scope para la carga masiva (courseId o degreeId)
    private Integer courseId;
    private Integer degreeId;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class BulkRecord {
        private String studentId;
        private StatusAttendance status;
        private Integer minutesLate;
        private String justification;
        private String source;
    }
}
