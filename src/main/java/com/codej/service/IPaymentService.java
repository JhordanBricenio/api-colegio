package com.codej.service;

import com.codej.model.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPaymentService {
    public List<Payment> findAll();
    public Payment findById(Integer id);
    public ResponseEntity<?> save (Payment payment);

    public Payment update(Payment payment, Integer id);
    public void delete(Integer id);
    public Page<Payment> findAll(Pageable pageable);
}
