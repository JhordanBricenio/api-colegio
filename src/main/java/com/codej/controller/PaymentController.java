package com.codej.controller;


import com.codej.dto.PaymentDTO;
import com.codej.mapper.PaymentMapper;
import com.codej.model.Payment;
import com.codej.service.IPaymentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.codej.constants.ApiConstants.ID_IN_PATH;
import static com.codej.constants.ApiConstants.PAYMENT_BASE;

@RestController
@RequestMapping(PAYMENT_BASE)
@AllArgsConstructor
public class PaymentController {

    private final IPaymentService paymentService;
    private final PaymentMapper paymentMapper;


   @GetMapping
    public ResponseEntity< List<PaymentDTO>> findAll() throws Exception {
        return ResponseEntity.ok(paymentMapper.mapIn(paymentService.findAll()));
    }
    @PostMapping
    public ResponseEntity<PaymentDTO> save(@Valid @RequestBody PaymentDTO paymentDTO) throws Exception {
        Payment payment= paymentMapper.mapOut(paymentDTO);
        Payment savedPayment = paymentService.save(payment);
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentMapper.mapIn(savedPayment));
    }
    @GetMapping(ID_IN_PATH)
    public ResponseEntity<PaymentDTO> findById(@PathVariable UUID id) throws Exception {
        return ResponseEntity.ok(paymentMapper.mapIn(paymentService.findById(id)));
    }

    @PutMapping(ID_IN_PATH)
    public ResponseEntity<PaymentDTO> update(@Valid @RequestBody PaymentDTO paymentDTO,@PathVariable UUID id)
            throws Exception {
        Payment payment = paymentMapper.mapOut(paymentDTO);
        Payment updatedPayment = paymentService.update(payment, id);
        return ResponseEntity.ok(paymentMapper.mapIn(updatedPayment));
    }
    @DeleteMapping(ID_IN_PATH)
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws Exception {
        paymentService.delete(id);
        return  ResponseEntity.noContent().build();
    }



}
