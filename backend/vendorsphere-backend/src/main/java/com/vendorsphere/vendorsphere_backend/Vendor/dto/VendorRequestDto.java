package com.vendorsphere.vendorsphere_backend.Vendor.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorRequestDto {

    @NotBlank(message = "Vendor name is required")
    @Size(max = 120, message = "Vendor name must be at most 120 characters")
    private String vendorName;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email address")
    @Size(max = 254, message = "Email must be at most 254 characters")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[+0-9().\\-\\s]{7,25}$", message = "Enter a valid phone number")
    private String phoneNumber;

    @NotBlank(message = "Address is required")
    @Size(max = 300, message = "Address must be at most 300 characters")
    private String address;

    @NotBlank(message = "Industry is required")
    @Size(max = 100, message = "Industry must be at most 100 characters")
    private String industry;
}
