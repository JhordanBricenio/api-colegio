package com.codej.service.impl;

import com.codej.model.PasswordReset;
import com.codej.model.User;
import com.codej.repository.IPasswordResetRepository;
import com.codej.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl {

    private final IUserRepository userRepository;
    private final IPasswordResetRepository passwordResetRepository;

    public Optional<PasswordReset> createPasswordResetForIdentifier(String identifier, long minutesToExpire) {
        User user = null;
        try { user = userRepository.findByDni(identifier);} catch (Exception ignored) {}
        if (user == null) {
            try { user = userRepository.findByEmail(identifier);} catch (Exception ignored) {}
        }
        if (user == null) return Optional.empty();

        PasswordReset pr = new PasswordReset();
        pr.setToken(UUID.randomUUID().toString());
        pr.setUser(user);
        pr.setExpiresAt(LocalDateTime.now().plusMinutes(minutesToExpire));
        PasswordReset saved = passwordResetRepository.save(pr);
        return Optional.of(saved);
    }

    public Optional<User> validateResetAndSetPassword(String token, String newPassword, org.springframework.security.crypto.password.PasswordEncoder encoder) {
        Optional<PasswordReset> prOpt = passwordResetRepository.findValidByToken(token, LocalDateTime.now());
        if (prOpt.isEmpty()) return Optional.empty();
        PasswordReset pr = prOpt.get();
        User user = pr.getUser();
        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
        passwordResetRepository.delete(pr);
        return Optional.of(user);
    }
}
