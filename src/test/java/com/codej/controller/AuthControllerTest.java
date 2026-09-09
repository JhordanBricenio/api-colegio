package com.codej.controller;

import com.codej.dto.AuthRequestDTO;
import com.codej.model.User;
import com.codej.security.CustomUserDetails;
import com.codej.security.CustomUserDetailsService;
import com.codej.security.JwtUtil;
import com.codej.repository.IUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthController authController;

    @Test
    void login_success_returnsToken() {
        AuthRequestDTO dto = new AuthRequestDTO();
        dto.setIdentifier("123");
        dto.setPassword("pass");

        User u = new User();
        u.setIdUser(java.util.UUID.randomUUID());
        u.setDni("123");
        u.setEmail("user@example.com");
        u.setPassword("encoded");

        when(userRepository.findByDni("123")).thenReturn(u);
        when(passwordEncoder.matches("pass", "encoded")).thenReturn(true);
        // AuthenticationManager.authenticate(...) devuelve Authentication; mockeamos un Authentication de retorno
        Authentication authMock = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authMock);

        CustomUserDetails cud = new CustomUserDetails(u);
        when(userDetailsService.loadUserByUsername("123")).thenReturn(cud);
        when(jwtUtil.generateTokenForUser(anyString(), anyString())).thenReturn("tok-1");

        ResponseEntity<?> resp = authController.login(dto);
        assertEquals(200, resp.getStatusCode().value());
        assertNotNull(resp.getBody());
    }

    @Test
    void login_invalidCredentials_returns401() {
        AuthRequestDTO dto = new AuthRequestDTO();
        dto.setIdentifier("123");
        dto.setPassword("wrong");

        when(userRepository.findByDni("123")).thenReturn(null);
        when(userRepository.findByEmail("123")).thenReturn(null);

        // simular que AuthenticationManager lanza BadCredentialsException
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad"));

        ResponseEntity<?> resp = authController.login(dto);
        assertEquals(401, resp.getStatusCode().value());
    }
}
