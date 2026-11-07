package com.vendorsphere.vendorsphere_backend.Auth.mapper;

import com.vendorsphere.vendorsphere_backend.Auth.dto.UserRequestDto;
import com.vendorsphere.vendorsphere_backend.Auth.dto.UserResponseDto;
import com.vendorsphere.vendorsphere_backend.Auth.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequestDto dto);
    UserResponseDto toResponseDto(User user);
}