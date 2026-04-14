package com.codej.dto.attendance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BulkAttendanceResponseDTO {
    private int created;
    private int updated;
    private int skipped;
    private List<String> errors;
}
