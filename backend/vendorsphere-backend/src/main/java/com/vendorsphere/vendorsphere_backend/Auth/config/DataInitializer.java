package com.vendorsphere.vendorsphere_backend.Auth.config;


import com.vendorsphere.vendorsphere_backend.Auth.entity.Role;
import com.vendorsphere.vendorsphere_backend.Auth.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {

        if(roleRepository.count() == 0){

            roleRepository.save(
                    Role.builder()
                            .name("ADMIN")
                            .build()
            );

            roleRepository.save(
                    Role.builder()
                            .name("AUDITOR")
                            .build()
            );

            roleRepository.save(
                    Role.builder()
                            .name("RISK_MANAGER")
                            .build()
            );

            roleRepository.save(
                    Role.builder()
                            .name("VENDOR_USER")
                            .build()
            );
        }
    }
}