package com.codej.repository;

import com.codej.model.PasswordReset;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface IPasswordResetRepository extends IGenericRepository<PasswordReset, UUID> {

    @Query("SELECT pr FROM PasswordReset pr WHERE pr.token = :token AND pr.expiresAt > :now")
    Optional<PasswordReset> findValidByToken(@Param("token") String token, @Param("now") LocalDateTime now);

}
