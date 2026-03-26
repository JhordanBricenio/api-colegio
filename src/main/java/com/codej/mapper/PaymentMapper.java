package com.codej.mapper;

import com.codej.dto.PaymentDTO;
import com.codej.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "idStudent", source = "student.idStudent")
    PaymentDTO mapIn(Payment payment);

    @Mapping(target = "student.idStudent", source = "idStudent")
    Payment mapOut(PaymentDTO paymentDTO);

    List<PaymentDTO> mapIn(List<Payment> payments);
}
