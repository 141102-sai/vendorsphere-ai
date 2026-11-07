package com.vendorsphere.vendorsphere_backend.Auth.controller;

import com.vendorsphere.vendorsphere_backend.Auth.Service.UserService;
import com.vendorsphere.vendorsphere_backend.Auth.dto.LoginRequestDto;
import com.vendorsphere.vendorsphere_backend.Auth.dto.LoginResponseDto;
import com.vendorsphere.vendorsphere_backend.Auth.dto.UserRequestDto;
import com.vendorsphere.vendorsphere_backend.Auth.dto.UserResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/create")
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto request) {
        UserResponseDto response =
                userService.createUser(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(
                userService.getAllUsers()
        );
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {
        return ResponseEntity.ok(
                userService.login(request)
        );
    }
}
