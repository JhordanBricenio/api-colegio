package com.codej.service.impl;

// ...existing imports...
import com.codej.model.PasswordReset;
import com.codej.model.User;
import com.codej.repository.IPasswordResetRepository;
import com.codej.repository.IUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IPasswordResetRepository passwordResetRepository;

    @InjectMocks
    private AuthServiceImpl authService;

    @Captor
    private ArgumentCaptor<PasswordReset> prCaptor;

    private AutoCloseable mocks;

    @BeforeEach
    void setup() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (mocks != null) mocks.close();
    }

    @Test
    void createPasswordResetForIdentifier_userFoundByDni_returnsOptionalWithToken() {
        User u = new User();
        u.setIdUser(java.util.UUID.randomUUID());
        u.setDni("12345678");
        when(userRepository.findByDni("12345678")).thenReturn(u);

        PasswordReset saved = new PasswordReset();
        saved.setToken("token-123");
        saved.setUser(u);
        saved.setExpiresAt(LocalDateTime.now().plusMinutes(60));
        when(passwordResetRepository.save(any(PasswordReset.class))).thenReturn(saved);

        Optional<PasswordReset> res = authService.createPasswordResetForIdentifier("12345678", 60);
        assertTrue(res.isPresent());
        assertEquals("token-123", res.get().getToken());
        verify(passwordResetRepository, times(1)).save(prCaptor.capture());
        assertEquals(u, prCaptor.getValue().getUser());
    }

    @Test
    void createPasswordResetForIdentifier_userNotFound_returnsEmpty() {
        when(userRepository.findByDni("nope")).thenReturn(null);
        when(userRepository.findByEmail("nope")).thenReturn(null);

        Optional<PasswordReset> res = authService.createPasswordResetForIdentifier("nope", 60);
        assertTrue(res.isEmpty());
        verify(passwordResetRepository, never()).save(any());
    }

    @Test
    void validateResetAndSetPassword_validToken_updatesPasswordAndDeletesReset() {
        User u = new User();
        u.setIdUser(java.util.UUID.randomUUID());
        u.setPassword("old");

        PasswordReset pr = new PasswordReset();
        pr.setToken("tok");
        pr.setUser(u);
        pr.setExpiresAt(LocalDateTime.now().plusMinutes(30));

        when(passwordResetRepository.findValidByToken(eq("tok"), any(LocalDateTime.class))).thenReturn(Optional.of(pr));
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        org.springframework.security.crypto.password.PasswordEncoder encoder = mock(org.springframework.security.crypto.password.PasswordEncoder.class);
        when(encoder.encode("newpass")).thenReturn("encoded-new");

        Optional<User> result = authService.validateResetAndSetPassword("tok", "newpass", encoder);
        assertTrue(result.isPresent());
        assertEquals("encoded-new", result.get().getPassword());
        verify(passwordResetRepository, times(1)).delete(pr);
        verify(userRepository, times(1)).save(u);
    }

    @Test
    void validateResetAndSetPassword_invalidToken_returnsEmpty() {
        when(passwordResetRepository.findValidByToken(eq("bad"), any(LocalDateTime.class))).thenReturn(Optional.empty());
        org.springframework.security.crypto.password.PasswordEncoder encoder = mock(org.springframework.security.crypto.password.PasswordEncoder.class);
        Optional<com.codej.model.User> res = authService.validateResetAndSetPassword("bad", "x", encoder);
        assertTrue(res.isEmpty());
        verify(userRepository, never()).save(any());
    }
}
