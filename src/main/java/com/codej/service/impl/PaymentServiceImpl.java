package com.codej.service.impl;

import com.codej.dto.PaymentByStudentDTO;
import com.codej.mapper.PaymentByStudentMapper;
import com.codej.model.Parent;
import com.codej.model.Payment;
import com.codej.repository.IGenericRepository;
import com.codej.repository.IPaymentRepository;
import com.codej.repository.IParentRepository;
import com.codej.service.IPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl extends CRUDGenericImpl<Payment, UUID> implements IPaymentService {

    private final IPaymentRepository paymentRepository;
    private final PaymentByStudentMapper paymentByStudentMapper;
    private final IParentRepository parentRepository;

    @Override
    protected IGenericRepository<Payment, UUID> getRepository() {
        return paymentRepository;
    }

    @Override
    public Page<PaymentByStudentDTO> findPaymentsByStudent(UUID idStudent, Pageable pageable) throws Exception {
        Page<Payment> payments = paymentRepository.findByStudent_IdStudent(idStudent, pageable);
        Page<PaymentByStudentDTO> dtoPage = payments.map(paymentByStudentMapper::toDto);

        // rellenar parent info consultando el repo
        Optional<Parent> parentOpt = parentRepository.findFirstByStudent_IdStudent(idStudent);
        if (parentOpt.isPresent()) {
            Parent parent = parentOpt.get();
            dtoPage.forEach(dto -> {
                dto.setParentRelationship(parent.getRelationship());
                dto.setIdParent(parent.getIdParent());
            });
        }
        return dtoPage;
    }
}
