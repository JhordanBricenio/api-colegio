package com.codej.service.impl;


import com.codej.dto.CourseRecordDTO;
import com.codej.model.Student;
import com.codej.model.StudentGrade;
import com.codej.model.TeacherSubjectAssignments;
import com.codej.repository.IGenericRepository;
import com.codej.repository.IStudentGradeRepository;
import com.codej.repository.IStudentRepository;
import com.codej.repository.ITeacherSubjectAssignmentsRepository;
import com.codej.service.IStudentGradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class StudentGradeServiceImpl extends CRUDGenericImpl<StudentGrade, UUID> implements IStudentGradeService {

    private final IStudentGradeRepository studentGradeRepository;
    private final IStudentRepository studentRepository;
    private final ITeacherSubjectAssignmentsRepository teacherSubjectAssignmentsRepository;

    @Override
    protected IGenericRepository<StudentGrade, UUID> getRepository() {
        return studentGradeRepository;
    }

    @Override
    public List<StudentGrade> findGradesByStudentId(UUID studentId) throws Exception {
        return studentGradeRepository.findByStudentId(studentId);
    }

    @Override
    public List<CourseRecordDTO> getCourseRecordsByStudentId(UUID studentId) throws Exception {
        Student student = studentRepository.findById(studentId).orElse(null);
        List<StudentGrade> grades = studentGradeRepository.findByStudentIdWithTeacher(studentId);

        Map<String, List<StudentGrade>> byCourse = new LinkedHashMap<>();

        for (StudentGrade sg : grades) {
            String courseKey;
            if (sg.getCourse() != null) {
                var c = sg.getCourse();
                courseKey = (c.getIdCourse() != null ? c.getIdCourse().toString() : "") + "::" + c.getName();
            } else {
                courseKey = inferCourseKey(sg, student);
            }
            byCourse.computeIfAbsent(courseKey, k -> new ArrayList<>()).add(sg);
        }

        List<CourseRecordDTO> result = new ArrayList<>();
        for (var entry : byCourse.entrySet()) {
            String courseKey = entry.getKey();
            List<StudentGrade> list = entry.getValue();

            Map<String, String> termMap = new LinkedHashMap<>();
            for (StudentGrade sg : list) {
                if (sg.getTerm() != null) termMap.put(sg.getTerm().name(), sg.getGrade());
            }

            BigDecimal avg = computeAverage(termMap.values());
            String status = determineStatus(avg, termMap);

            String[] parts = courseKey.split("::", 2);
            String courseId = parts.length > 0 && !parts[0].isEmpty() ? parts[0] : null;
            String courseName = parts.length > 1 ? parts[1] : "Unknown";

            CourseRecordDTO cr = new CourseRecordDTO(courseId, courseName, termMap, avg, status);
            result.add(cr);
        }

        return result;
    }

    private String inferCourseKey(StudentGrade sg, Student student) {
        if (sg.getTeacher() == null) return "::Unknown";
        UUID teacherId = sg.getTeacher().getIdTeacher();

        UUID degreeId = null;
        UUID educationLevelId = null;
        try {
            if (student != null) {
                if (student.getDegree() != null) degreeId = student.getDegree().getIdDegree();
                if (student.getEducationLevel() != null) educationLevelId = student.getEducationLevel().getIdEducationLevel();
            }
        } catch (Exception ignored) {
        }

        try {
            if (degreeId != null && educationLevelId != null) {
                List<TeacherSubjectAssignments> matches = teacherSubjectAssignmentsRepository.findByTeacherAndDegreeAndEducationLevel(teacherId, degreeId, educationLevelId);
                if (matches != null && !matches.isEmpty()) {
                    var c = matches.get(0).getCourse();
                    if (c != null) return (c.getIdCourse() != null ? c.getIdCourse().toString() : "") + "::" + c.getName();
                }
            }
        } catch (Exception ignored) {
        }

        try {
            List<TeacherSubjectAssignments> matches = teacherSubjectAssignmentsRepository.findByTeacher(teacherId);
            if (matches != null && !matches.isEmpty()) {
                var c = matches.get(0).getCourse();
                if (c != null) return (c.getIdCourse() != null ? c.getIdCourse().toString() : "") + "::" + c.getName();
            }
        } catch (Exception ignored) {
        }

        return "::Unknown";
    }

    private BigDecimal computeAverage(Collection<String> grades) {
        List<BigDecimal> nums = new ArrayList<>();
        for (String g : grades) {
            if (g == null) continue;
            try {
                nums.add(new BigDecimal(g));
            } catch (Exception ignored) {
            }
        }
        if (nums.isEmpty()) return null;
        BigDecimal sum = nums.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum.divide(BigDecimal.valueOf(nums.size()), 2, RoundingMode.HALF_UP);
    }

    private String determineStatus(BigDecimal avg, Map<String, String> termMap) {
        int termsRecorded = termMap.size();
        if (avg == null || termsRecorded < 4) return "IN_PROGRESS";
        return avg.compareTo(BigDecimal.valueOf(11)) >= 0 ? "APPROVED" : "FAILED";
    }
}
