package com.codej.service.impl;

import com.codej.model.Payment;
import com.codej.repository.IPaymentRepository;
import com.codej.service.IPaymentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements IPaymentService {

    private final IPaymentRepository paymentRepository;
    @Override
    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment findById(Integer id) {
        return paymentRepository.findById(id).orElse(null);
    }

    @Override
    public ResponseEntity<?> save(Payment payment) {
        Map<String, Object> response = new HashMap<>();
        Payment paymentNew = null;
        try {
            paymentNew = paymentRepository.save(payment);
        }catch (Exception e){
            response.put("mensaje", "Error al crear el pago");
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        response.put("mensaje", "El pago ha sido creado con éxito");
        response.put("proyecto", paymentNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @Override
    public Payment update(Payment payment, Integer id) {
        Payment paymentUpdate = findById(payment.getId());
        paymentUpdate.setAmount(payment.getAmount());
        paymentUpdate.setName(payment.getName());
        paymentUpdate.setDate(payment.getDate());
        paymentUpdate.setStatus(payment.getStatus());
        paymentUpdate.setDescription(payment.getDescription());
        return paymentRepository.save(payment);
    }

    @Override
    public void delete(Integer id) {
        paymentRepository.deleteById(id);
    }

    @Override
    public Page<Payment> findAll(Pageable pageable) {
        return paymentRepository.findAll(pageable);
    }
}
