package com.vendorsphere.vendorsphere_backend.Auth.Service;

import com.vendorsphere.vendorsphere_backend.Auth.entity.RefreshToken;
import com.vendorsphere.vendorsphere_backend.Auth.entity.User;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(User user);
    RefreshToken verifyExpiration(RefreshToken token);
}
