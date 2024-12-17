package com.codej.controller;


import com.codej.model.Payment;
import com.codej.service.IPaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class PaymentRestController {

    private final IPaymentService paymentService;

    @GetMapping("/payment")
    public List<Payment> payment(){
        return paymentService.findAll();
    }

    @PostMapping("/payment")
    public ResponseEntity<?> save( @RequestBody Payment payment){
        return paymentService.save(payment);
    }

    @GetMapping("/payment/{id}")
    public Payment findById(@PathVariable Integer id){
        return paymentService.findById(id);
    }

    @PutMapping("/payment/{id}")
    public Payment update(@RequestBody Payment payment, @PathVariable Integer id){
        return paymentService.update(payment, id);
    }

    @DeleteMapping("/payment/{id}")
    public void delete(@PathVariable Integer id){
        paymentService.delete(id);
    }

}
