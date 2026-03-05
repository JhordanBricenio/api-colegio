package com.codej.service;


import com.codej.model.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ITeacherService extends ICRUDService<Teacher, UUID> {

    Teacher saveTeacher(Teacher user) throws  Exception;

    Teacher findByDni(String dni) throws Exception;

    Page<Teacher> findAllPaged(Pageable pageable) throws Exception;

}
