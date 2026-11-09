package com.vendorsphere.vendorsphere_backend.Auth.config;


import com.vendorsphere.vendorsphere_backend.Auth.entity.Role;
import com.vendorsphere.vendorsphere_backend.Auth.entity.RoleType;
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

        for (RoleType type : RoleType.values()) {
            if (roleRepository.findByName(type.name()).isEmpty()) {
                roleRepository.save(Role.builder().name(type.name()).build());
            }
        }
    }
}
