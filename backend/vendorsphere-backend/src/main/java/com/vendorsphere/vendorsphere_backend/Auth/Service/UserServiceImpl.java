package com.vendorsphere.vendorsphere_backend.Auth.Service;

import com.vendorsphere.vendorsphere_backend.Auth.Exception.UserAlreadyExistsException;
import com.vendorsphere.vendorsphere_backend.Auth.dto.LoginRequestDto;
import com.vendorsphere.vendorsphere_backend.Auth.dto.LoginResponseDto;
import com.vendorsphere.vendorsphere_backend.Auth.dto.UserRequestDto;
import com.vendorsphere.vendorsphere_backend.Auth.dto.UserResponseDto;
import com.vendorsphere.vendorsphere_backend.Auth.entity.User;
import com.vendorsphere.vendorsphere_backend.Auth.mapper.UserMapper;
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

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final UserMapper userMapper;
    private final CustomUserDetailsService customUserDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto createUser(
            UserRequestDto request) {

        if (userRepository
                .findByEmail(request.getEmail())
                .isPresent()) {

            throw new UserAlreadyExistsException(
                    "User already exists with email : "
                            + request.getEmail()
            );
        }

        User user = userMapper.toEntity(request);

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

//    @Override
//    public LoginResponseDto login(
//            LoginRequestDto request
//    ) {
//
//        authenticationManager.authenticate(
//
//                new UsernamePasswordAuthenticationToken(
//
//                        request.getEmail(),
//
//                        request.getPassword()
//                )
//        );
//
//        return LoginResponseDto.builder()
//                .message("Login Successful")
//                .email(request.getEmail())
//                .build();
//    }

    @Override
    public LoginResponseDto login(
            LoginRequestDto request
    ) {

        authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        UserDetails userDetails =
                customUserDetailsService
                        .loadUserByUsername(
                                request.getEmail()
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
