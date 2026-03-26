package com.codej.service;

import com.codej.model.Payment;
import com.codej.dto.PaymentByStudentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IPaymentService extends ICRUDService<Payment, UUID>{
    Page<PaymentByStudentDTO> findPaymentsByStudent(UUID idStudent, Pageable pageable) throws Exception;
}
