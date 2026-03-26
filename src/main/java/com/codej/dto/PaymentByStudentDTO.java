package com.codej.dto;

import com.codej.emuns.PaymentMethod;
import com.codej.emuns.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PaymentByStudentDTO {

    private UUID idPayment;
    private LocalDate paymentDate;
    private PaymentStatus status;
    private String paidMonth;
    private Integer paidYear;
    private BigDecimal amountPaid;
    private PaymentMethod paymentMethod;
    private String receiptNumber;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Student related
    private UUID idStudent;
    private String studentCode;
    private String studentDni;
    private String studentPhone;

    // Parent info (if any) - we'll return the first parent relationship found
    private String parentRelationship;
    private UUID idParent;

    // Degree and education level
    private String degreeName;
    private String educationLevelName;

}
