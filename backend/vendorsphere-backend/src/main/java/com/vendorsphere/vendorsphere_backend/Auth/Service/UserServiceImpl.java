package com.vendorsphere.vendorsphere_backend.Auth.Service;

import com.vendorsphere.vendorsphere_backend.Auth.Exception.UserAlreadyExistsException;
import com.vendorsphere.vendorsphere_backend.Auth.dto.LoginRequestDto;
import com.vendorsphere.vendorsphere_backend.Auth.dto.LoginResponseDto;
import com.vendorsphere.vendorsphere_backend.Auth.dto.UserRequestDto;
import com.vendorsphere.vendorsphere_backend.Auth.dto.UserResponseDto;
import com.vendorsphere.vendorsphere_backend.Auth.entity.User;
import com.vendorsphere.vendorsphere_backend.Auth.entity.RoleType;
import com.vendorsphere.vendorsphere_backend.Auth.mapper.UserMapper;
import com.vendorsphere.vendorsphere_backend.Auth.repository.RoleRepository;
import com.vendorsphere.vendorsphere_backend.Auth.repository.UserRepository;
import com.vendorsphere.vendorsphere_backend.Auth.security.CustomUserDetailsService;
import com.vendorsphere.vendorsphere_backend.Auth.security.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JWTService jwtService;
    private final UserMapper userMapper;
    private final CustomUserDetailsService customUserDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto createUser(
            UserRequestDto request) {

        String email = request.getEmail().trim().toLowerCase();
        if (userRepository
                .findByEmail(email)
                .isPresent()) {

            throw new UserAlreadyExistsException(
                    "User already exists with email : "
                            + email
            );
        }

        User user = userMapper.toEntity(request);
        user.setEmail(email);
        user.setActive(true);
        user.setRoles(Set.of(roleRepository.findByName(RoleType.VENDOR_USER.name())
                .orElseThrow(() -> new IllegalStateException("Default VENDOR_USER role is not configured"))));

        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        User savedUser =
                userRepository.save(user);

        return userMapper
                .toResponseDto(savedUser);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponseDto)
                .toList();
    }

    @Override
    public LoginResponseDto login(
            LoginRequestDto request
    ) {

        String email = request.getEmail().trim().toLowerCase();
        authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(
                        email,
                        request.getPassword()
                )
        );

        UserDetails userDetails =
                customUserDetailsService
                        .loadUserByUsername(
                                email
                        );

        String token =
                jwtService.generateToken(
                        userDetails
                );

        return LoginResponseDto
                .builder()
                .token(token)
                .build();
    }
}
