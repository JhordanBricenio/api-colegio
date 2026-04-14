package com.codej.controller;

import com.codej.dto.AuthRequestDTO;
import com.codej.dto.AuthResponseDTO;
import com.codej.dto.UserSummaryDTO;
import com.codej.model.User;
import com.codej.repository.IUserRepository;
import com.codej.security.CustomUserDetails;
import com.codej.security.JwtUtil;
import com.codej.service.IUserService;
import com.codej.service.impl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final com.codej.security.CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final IUserService userService;
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthServiceImpl authService;

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO dto) {
        log.debug("Login attempt for identifier={}", dto.getIdentifier());
        try {
            // debug: comprobar si el usuario existe y si el password coincide con el hash en la DB
            User user = userRepository.findByDni(dto.getIdentifier());
            if (user == null) user = userRepository.findByEmail(dto.getIdentifier());
            if (user != null) {
                boolean matches = passwordEncoder.matches(dto.getPassword(), user.getPassword());
                log.debug("Found user id={} email={} passwordMatches={}", user.getIdUser(), user.getEmail(), matches);
            } else {
                log.debug("No user record found for identifier={}", dto.getIdentifier());
            }

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getIdentifier(), dto.getPassword()));
            var userDetails = userDetailsService.loadUserByUsername(dto.getIdentifier());
            CustomUserDetails cud = (CustomUserDetails) userDetails;
            User u = cud.getUser();
            String token = jwtUtil.generateTokenForUser(u.getDni() != null ? u.getDni() : u.getEmail(), u.getRole() != null ? u.getRole().getName().name() : "USER");
            UserSummaryDTO us = new UserSummaryDTO();
            us.setId(u.getIdUser().toString());
            us.setName(u.getName());
            us.setLastname(u.getLastname());
            us.setEmail(u.getEmail());
            us.setRole(u.getRole() != null ? u.getRole().getName().name(): "USER");
            AuthResponseDTO response = new AuthResponseDTO(token, LocalDateTime.now().plusSeconds(3600), us);
            log.debug("Login success for identifier={}, userId={}", dto.getIdentifier(), u.getIdUser());
            return ResponseEntity.ok(response);
        } catch (AuthenticationException ex) {
            log.warn("Authentication failed for identifier={}: {}", dto.getIdentifier(), ex.getMessage());
            log.debug("Authentication exception details", ex);
            return ResponseEntity.status(401).body("Credenciales inválidas");
        } catch (Exception ex) {
            log.error("Internal error during login for identifier={}", dto.getIdentifier(), ex);
            return ResponseEntity.status(500).body("Error interno: " + ex.getMessage());
        }
    }

    @PostMapping("/recover")
    public ResponseEntity<?> recoverByIdentifier(@RequestParam String identifier) {
        Optional<com.codej.model.PasswordReset> pr = authService.createPasswordResetForIdentifier(identifier, 60);
        if (pr.isEmpty()) return ResponseEntity.ok("Si el identificador existe se enviará un enlace de recuperación");
        // En producción: envía correo con token; aquí devolvemos token en respuesta para pruebas
        return ResponseEntity.ok("Token de recuperación: " + pr.get().getToken());
    }

    @PostMapping("/reset")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        Optional<User> u = authService.validateResetAndSetPassword(token, newPassword, passwordEncoder);
        if (u.isEmpty()) return ResponseEntity.status(400).body("Token inválido o expirado");
        return ResponseEntity.ok("Contraseña actualizada");
    }
}
