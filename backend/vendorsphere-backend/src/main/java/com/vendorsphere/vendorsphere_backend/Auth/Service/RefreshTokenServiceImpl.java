package com.vendorsphere.vendorsphere_backend.Auth.Service;

import com.vendorsphere.vendorsphere_backend.Auth.entity.RefreshToken;
import com.vendorsphere.vendorsphere_backend.Auth.entity.User;
import com.vendorsphere.vendorsphere_backend.Auth.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = RefreshToken.builder()
                        .token(UUID.randomUUID().toString())
                        .expiryDate(LocalDateTime.now().plusDays(7))
                        .user(user)
                        .build();
        return refreshTokenRepository
                .save(refreshToken);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if(token.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh Token Expired");
        }
        return token;
    }
}
