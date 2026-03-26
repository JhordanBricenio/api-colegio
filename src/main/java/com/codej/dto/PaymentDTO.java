package com.codej.dto;

import com.codej.emuns.PaymentMethod;
import com.codej.emuns.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class PaymentDTO {

    private UUID idPayment;

    @NotNull
    private LocalDate paymentDate;

    @NotNull
    private PaymentStatus status;

    @NotNull
    private String paidMonth;

    private Integer paidYear;

    @NotNull
    private BigDecimal amountPaid;

    @NotNull
    private PaymentMethod paymentMethod;

    @NotNull
    private String receiptNumber;

    private String notes;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String idStudent;
}
