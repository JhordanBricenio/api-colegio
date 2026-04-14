package com.codej.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class YearRecordDTO {
    private int year;
    private List<CourseRecordDTO> courses;
    private SummaryDTO summary;
}
