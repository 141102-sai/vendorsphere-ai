package com.vendorsphere.vendorsphere_backend.Auth.repository;

import com.vendorsphere.vendorsphere_backend.Auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findByToken(
            String token
    );
}
