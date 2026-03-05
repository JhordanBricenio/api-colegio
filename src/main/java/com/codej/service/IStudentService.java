package com.codej.service;


import com.codej.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IStudentService extends ICRUDService<Student, UUID> {

    Student saveStudent(Student user) throws  Exception;

    Student findByDni(String dni) throws Exception;

    Page<Student> findAllPaged(Pageable pageable) throws Exception;

}
