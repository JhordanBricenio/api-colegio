package com.codej.services;

import com.codej.models.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPaymentService {
    public List<Payment> findAll();
    public Payment findById(Integer id);
    public Payment save (Payment payment);
    public void delete(Integer id);
    public Page<Payment> findAll(Pageable pageable);
}
