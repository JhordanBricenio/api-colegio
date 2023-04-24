package com.codej.services.impl;

import com.codej.models.Payment;
import com.codej.services.IPaymentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements IPaymentService {
    @Override
    public List<Payment> findAll() {
        return null;
    }

    @Override
    public Payment findById(Integer id) {
        return null;
    }

    @Override
    public Payment save(Payment payment) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Page<Payment> findAll(Pageable pageable) {
        return null;
    }
}
