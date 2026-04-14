package com.codej.config;

import com.codej.emuns.Gender;
import com.codej.emuns.RoleName;
import com.codej.model.Role;
import com.codej.model.User;
import com.codej.repository.IRoleRepository;
import com.codej.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
  // private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Crear roles si no existen
        //createRoleIfNotExists(RoleName.ADMIN);
      //  createRoleIfNotExists(RoleName.TEACHER);
       // createRoleIfNotExists(RoleName.STUDENT);
       // createRoleIfNotExists(RoleName.PARENT);

        // Crear usuario admin si no existe
        String adminEmail = "jhordanbriceno@hotmail.com";
        if (!userRepository.existsByEmail(adminEmail)) {
            Role adminRole = roleRepository.findByName("ADMIN").orElseThrow();

            User admin = new User();
            admin.setName("Jhordan");
            admin.setLastname("Briceño");
            admin.setDni("00000000");
            admin.setBirthDate(LocalDate.of(1990,1,1));
            admin.setEmail(adminEmail);
            // contraseña por defecto: admin123 (codificada)
            admin.setPassword(new BCryptPasswordEncoder().encode("admin123"));
            admin.setPhone("000000000");
            admin.setAddress("N/A");
            admin.setPhoto(null);
            admin.setGender(Gender.MASCULINO);
            admin.setRole(adminRole);

            userRepository.save(admin);
            System.out.println("[DataInitializer] Usuario admin creado: " + adminEmail + " / password: admin123");
        } else {
            System.out.println("[DataInitializer] Usuario admin ya existe: " + adminEmail);
        }
    }

    private void createRoleIfNotExists(RoleName roleName) {
        Optional<Role> existing = roleRepository.findByName(roleName.name());
        if (existing.isEmpty()) {
            Role r = new Role();
            r.setName(roleName);
            roleRepository.save(r);
            System.out.println("[DataInitializer] Role creado: " + roleName);
        }
    }
}
