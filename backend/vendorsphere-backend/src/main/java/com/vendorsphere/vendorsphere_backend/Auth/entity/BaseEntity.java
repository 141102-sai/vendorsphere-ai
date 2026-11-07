package com.vendorsphere.vendorsphere_backend.Auth.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public  abstract class BaseEntity {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
