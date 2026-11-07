package com.vendorsphere.vendorsphere_backend.Auth.Service;

import com.vendorsphere.vendorsphere_backend.Auth.dto.LoginRequestDto;
import com.vendorsphere.vendorsphere_backend.Auth.dto.LoginResponseDto;
import com.vendorsphere.vendorsphere_backend.Auth.dto.UserRequestDto;
import com.vendorsphere.vendorsphere_backend.Auth.dto.UserResponseDto;

import java.util.List;

public interface UserService {
     UserResponseDto createUser(UserRequestDto request);
     List<UserResponseDto> getAllUsers();
     LoginResponseDto login(
             LoginRequestDto request
     );

}
