package com.codej.service.impl;

import com.codej.dto.*;
import com.codej.exceptions.ResourceNotFoundException;
import com.codej.model.Parent;
import com.codej.model.Student;
import com.codej.model.StudentGrade;
import com.codej.model.TeacherSubjectAssignments;
import com.codej.repository.IParentRepository;
import com.codej.repository.IStudentGradeRepository;
import com.codej.repository.IStudentRepository;
import com.codej.repository.ITeacherSubjectAssignmentsRepository;
import com.codej.service.IStudentKardexService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class StudentKardexServiceImpl implements IStudentKardexService {

    private final IStudentRepository studentRepository;
    private final IStudentGradeRepository studentGradeRepository;
    private final ITeacherSubjectAssignmentsRepository teacherSubjectAssignmentsRepository;
    private final IParentRepository parentRepository;

    @Override
    public KardexProfessionalDTO getKardexByStudentId(UUID studentId) throws Exception {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el estudiante con id: " + studentId));

        return buildKardexForStudent(student);
    }

    @Override
    public KardexProfessionalDTO getKardexByStudentDni(String dni) throws Exception {
        Student student = studentRepository.findStudentByDni(dni)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el estudiante con DNI: " + dni));
        return buildKardexForStudent(student);
    }

    private KardexProfessionalDTO buildKardexForStudent(Student student) {
        UUID studentId = student.getIdStudent();
        String code = student.getCode();
        String fullName = student.getUser().getName() + " " + student.getUser().getLastname();

        List<StudentGrade> grades = studentGradeRepository.findByStudentIdWithTeacher(studentId);

        Map<Integer, Map<String, List<StudentGrade>>> byYearCourse = new TreeMap<>();

        for (StudentGrade sg : grades) {
            LocalDateTime created = sg.getCreatedAt();
            int year = created != null ? created.getYear() : LocalDateTime.now().getYear();

            String courseKey;
            if (sg.getCourse() != null) {
                var c = sg.getCourse();
                courseKey = (c.getIdCourse() != null ? c.getIdCourse().toString() : "") + "::" + c.getName();
            } else {
                courseKey = inferCourseKey(sg, student);
            }

            byYearCourse.computeIfAbsent(year, y -> new LinkedHashMap<>())
                    .computeIfAbsent(courseKey, k -> new ArrayList<>()).add(sg);
        }

        List<YearRecordDTO> records = new ArrayList<>();

        for (var entryYear : byYearCourse.entrySet()) {
            int year = entryYear.getKey();
            List<CourseRecordDTO> courseRecords = new ArrayList<>();

            int approved = 0;
            int failed = 0;
            int inprogress = 0;
            BigDecimal gpaSum = BigDecimal.ZERO;
            int coursesCount = 0;

            for (var courseEntry : entryYear.getValue().entrySet()) {
                String courseKey = courseEntry.getKey();
                List<StudentGrade> list = courseEntry.getValue();

                // build term map
                Map<String, String> termMap = new HashMap<>();
                for (StudentGrade sg : list) {
                    termMap.put(sg.getTerm().name(), sg.getGrade());
                }

                // compute average across numeric grades
                BigDecimal avg = computeAverage(termMap.values());

                String status = determineStatus(avg, termMap);
                if ("APPROVED".equals(status)) approved++;
                else if ("FAILED".equals(status)) failed++;
                else inprogress++;

                if (avg != null) {
                    gpaSum = gpaSum.add(avg);
                    coursesCount++;
                }

                String[] parts = courseKey.split("::", 2);
                String courseId = parts.length > 0 && !parts[0].isEmpty() ? parts[0] : null;
                String courseName = parts.length > 1 ? parts[1] : "Unknown";

                CourseRecordDTO cr = new CourseRecordDTO(courseId, courseName, termMap, avg, status);
                courseRecords.add(cr);
            }

            BigDecimal gpa = coursesCount > 0 ? gpaSum.divide(BigDecimal.valueOf(coursesCount), 2, RoundingMode.HALF_UP) : BigDecimal.ZERO;
            SummaryDTO summary = new SummaryDTO(gpa, approved, failed, inprogress);
            YearRecordDTO yr = new YearRecordDTO(year, courseRecords, summary);
            records.add(yr);
        }

        // Build enriched StudentSummaryDTO using available relations and parent lookup
        StudentSummaryDTO ss = new StudentSummaryDTO();
        ss.setId(studentId.toString());
        ss.setCode(code);
        ss.setFullName(fullName);

        if (student.getUser() != null) {
            ss.setStudentDni(student.getUser().getDni());
            ss.setStudentPhone(student.getUser().getPhone());
            ss.setStudentEmail(student.getUser().getEmail());
        }

        if (student.getDegree() != null) {
            ss.setDegreeId(student.getDegree().getIdDegree() != null ? student.getDegree().getIdDegree().toString() : null);
            ss.setDegreeCourse(student.getDegree().getCourse());
            ss.setDegreeSection(student.getDegree().getSection());
        }

        if (student.getEducationLevel() != null) {
            ss.setEducationLevelId(student.getEducationLevel().getIdEducationLevel() != null ? student.getEducationLevel().getIdEducationLevel().toString() : null);
            ss.setEducationLevelName(student.getEducationLevel().getName());
        }

        try {
            parentRepository.findFirstByStudent_IdStudent(studentId).ifPresent(p -> {
                if (p.getUser() != null) {
                    ss.setParentId(p.getIdParent() != null ? p.getIdParent().toString() : null);
                    ss.setParentFullName(p.getUser().getName() + " " + p.getUser().getLastname());
                    ss.setParentPhone(p.getUser().getPhone());
                    ss.setParentDni(p.getUser().getDni());
                    ss.setParentRelationship(p.getRelationship());
                }
            });
        } catch (Exception ignored) {
        }

        return new KardexProfessionalDTO(ss, records);
    }

    private String inferCourseKey(StudentGrade sg, Student student) {
        if (sg.getTeacher() == null) return "::Unknown";
        UUID teacherId = sg.getTeacher().getIdTeacher();

        UUID degreeId = null;
        UUID educationLevelId = null;
        try {
            if (student.getDegree() != null) degreeId = student.getDegree().getIdDegree();
            if (student.getEducationLevel() != null) educationLevelId = student.getEducationLevel().getIdEducationLevel();
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
        // business rule: approved if avg >= 11
        return avg.compareTo(BigDecimal.valueOf(11)) >= 0 ? "APPROVED" : "FAILED";
    }
}
