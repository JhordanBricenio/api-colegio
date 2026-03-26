package com.codej.repository;

import com.codej.model.Payment;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPaymentRepository extends IGenericRepository<Payment, UUID> {

    Page<Payment> findByStudent_IdStudent(UUID idStudent, Pageable pageable);

}
