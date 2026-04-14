package com.codej.dto.attendance;

import com.codej.emuns.StatusAttendance;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BulkAttendanceRequestDTO {
    private String date; // yyyy-MM-dd
    private String session;
    private List<BulkRecord> records;
    private String conflictStrategy; // skip | update | fail

    @Getter
    @Setter
    @NoArgsConstructor
    public static class BulkRecord {
        private String teacherId;
        private StatusAttendance status;
        private Integer minutesLate;
        private String justification;
        private String source;
    }
}
