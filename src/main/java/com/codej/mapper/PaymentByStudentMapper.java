package com.codej.mapper;

import com.codej.dto.PaymentByStudentDTO;
import com.codej.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentByStudentMapper {

    @Mapping(target = "idStudent", source = "student.idStudent")
    @Mapping(target = "studentCode", source = "student.code")
    @Mapping(target = "studentDni", source = "student.user.dni")
    @Mapping(target = "studentPhone", source = "student.user.phone")
    @Mapping(target = "degreeName", source = "student.degree.course")
    @Mapping(target = "educationLevelName", source = "student.educationLevel.name")
    PaymentByStudentDTO toDto(Payment payment);

    List<PaymentByStudentDTO> toDtoList(List<Payment> payments);

}
