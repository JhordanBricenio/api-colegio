package com.codej.security;

import com.codej.model.User;
import com.codej.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final IUserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        User user = userRepository.findByDniWithRole(identifier);
        if (user == null) {
            user = userRepository.findByEmailWithRole(identifier);
        }
        if (user == null) {
            log.warn("Usuario no encontrado para identifier={}", identifier);
            throw new UsernameNotFoundException("Usuario no encontrado: " + identifier);
        }
        log.debug("Usuario encontrado identifier={}, id={}, email={}", identifier, user.getIdUser(), user.getEmail());
        return new CustomUserDetails(user);
    }
}
