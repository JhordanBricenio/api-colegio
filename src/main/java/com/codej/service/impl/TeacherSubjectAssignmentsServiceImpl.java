package com.codej.service.impl;


import com.codej.emuns.EducationLevelType;
import com.codej.exceptions.BadRequestException;
import com.codej.exceptions.ResourceNotFoundException;
import com.codej.model.TeacherSubjectAssignments;
import com.codej.repository.IGenericRepository;
import com.codej.repository.ITeacherSubjectAssignmentsRepository;
import com.codej.repository.ITeacherRepository;
import com.codej.repository.IDegreeRepository;
import com.codej.repository.ICourseRepository;
import com.codej.repository.IEducationLevelRepository;
import com.codej.service.ITeacherSubjectAssignmentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class TeacherSubjectAssignmentsServiceImpl extends CRUDGenericImpl<TeacherSubjectAssignments, UUID> implements ITeacherSubjectAssignmentsService {

    private final ITeacherSubjectAssignmentsRepository teacherSubjectAssignmentsRepository;
    private final ITeacherRepository teacherRepository;
    private final IDegreeRepository degreeRepository;
    private final ICourseRepository courseRepository;
    private final IEducationLevelRepository educationLevelRepository;

    @Override
    protected IGenericRepository<TeacherSubjectAssignments, UUID> getRepository() {
        return teacherSubjectAssignmentsRepository;
    }

    @Override
    public TeacherSubjectAssignments save(TeacherSubjectAssignments t) throws Exception {
        attachAndValidate(t);
        return super.save(t);
    }

    @Override
    public TeacherSubjectAssignments update(TeacherSubjectAssignments t, UUID id) throws Exception {
        // ensure exists
        getRepository().findById(id).orElseThrow(() -> new ResourceNotFoundException("No se encontró la asignación con id: " + id));
        attachAndValidate(t);
        return super.update(t, id);
    }

    @Override
    public List<TeacherSubjectAssignments> findByTeacher(UUID teacherId) throws Exception {
        return teacherSubjectAssignmentsRepository.findByTeacher(teacherId);
    }

    private void attachAndValidate(TeacherSubjectAssignments t) {
        // Validate teacher exists
        UUID teacherId = t.getTeacher() != null ? t.getTeacher().getIdTeacher() : null;
        if (teacherId == null) throw new BadRequestException("teacherId es requerido");
        var teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el docente con id: " + teacherId));
        t.setTeacher(teacher);

        // If a course object was provided but has no id, ignore it (treat as null)
        if (t.getCourse() != null && t.getCourse().getIdCourse() == null) {
            t.setCourse(null);
        }

        // If a degree object was provided but has no id, ignore it
        if (t.getDegree() != null && t.getDegree().getIdDegree() == null) {
            t.setDegree(null);
        }

        // If an educationLevel object was provided but has no id, ignore it
        if (t.getEducationLevel() != null && t.getEducationLevel().getIdEducationLevel() == null) {
            t.setEducationLevel(null);
        }

        // Attach degree if present
        boolean hasDegree = false;
        if (t.getDegree() != null && t.getDegree().getIdDegree() != null) {
            UUID degreeId = t.getDegree().getIdDegree();
            var degree = degreeRepository.findById(degreeId)
                    .orElseThrow(() -> new ResourceNotFoundException("No se encontró el grado con id: " + degreeId));
            t.setDegree(degree);
            // Ensure educationLevel coherence
            var edu = degree.getEducationLevel();
            if (edu == null) throw new BadRequestException("Degree no tiene educationLevel asociado");
            hasDegree = true;
            // IMPORTANT: also set the educationLevel on the assignment so it gets persisted and is available
            // when reading assignments for reporting (e.g., to know PRIMARY/INITIAL etc.).
            t.setEducationLevel(edu);
        }

        // Attach course if present
        boolean hasCourse = false;
        if (t.getCourse() != null && t.getCourse().getIdCourse() != null) {
            UUID courseId = t.getCourse().getIdCourse();
            var course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new ResourceNotFoundException("No se encontró el curso con id: " + courseId));
            t.setCourse(course);
            hasCourse = true;
        }
        if (t.getEducationLevel() != null && t.getEducationLevel().getIdEducationLevel() != null) {
            UUID eduId = t.getEducationLevel().getIdEducationLevel();
            var edu = educationLevelRepository.findById(eduId)
                    .orElseThrow(() -> new ResourceNotFoundException("No se encontró el nivel educativo con id: " + eduId));
            t.setEducationLevel(edu);
        }
        if (hasCourse && !hasDegree && (t.getEducationLevel() == null)) {
            List<TeacherSubjectAssignments> existing = teacherSubjectAssignmentsRepository.findByTeacher(teacherId);
            Set<UUID> foundEduIds = new HashSet<>();
            for (TeacherSubjectAssignments ex : existing) {
                // Prefer direct educationLevel on assignment
                if (ex.getEducationLevel() != null && ex.getEducationLevel().getIdEducationLevel() != null) {
                    foundEduIds.add(ex.getEducationLevel().getIdEducationLevel());
                } else if (ex.getDegree() != null && ex.getDegree().getEducationLevel() != null && ex.getDegree().getEducationLevel().getIdEducationLevel() != null) {
                    foundEduIds.add(ex.getDegree().getEducationLevel().getIdEducationLevel());
                }
            }
            if (foundEduIds.size() == 1) {
                UUID inferredEduId = foundEduIds.iterator().next();
                var edu = educationLevelRepository.findById(inferredEduId)
                        .orElseThrow(() -> new ResourceNotFoundException("El educationLevel inferido no existe: " + inferredEduId));
                t.setEducationLevel(edu);
            } else if (foundEduIds.isEmpty()) {
                throw new BadRequestException("No se pudo inferir educationLevel a partir de asignaciones previas del docente. Incluya 'educationLevelId' en el payload.");
            } else {
                throw new BadRequestException("No se puede inferir un único educationLevel porque el docente tiene asignaciones en varios niveles. Por favor incluya 'educationLevelId' en el payload para esta asignación.");
            }
        }

        // Validation: at least degree or course must be present
        if (!hasDegree && !hasCourse) {
            throw new BadRequestException("Se requiere degreeId o courseId para la asignación");
        }

        // Determine effective education level type (degree -> degree.educationLevel, else explicit edu)
        var effective = (hasDegree) ? t.getDegree().getEducationLevel().getLevelType() : (t.getEducationLevel() != null ? t.getEducationLevel().getLevelType() : null);

        // If effective indicates PRIMARY/INITIAL enforce degree assignment
        if ((effective == EducationLevelType.INITIAL || effective == EducationLevelType.PRIMARY) && !hasDegree) {
            throw new BadRequestException("Para niveles INITIAL/PRIMARY la asignación debe realizarse por grado (degreeId)");
        }

    }

}
