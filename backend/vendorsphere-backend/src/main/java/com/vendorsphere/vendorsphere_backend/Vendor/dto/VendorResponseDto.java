package com.vendorsphere.vendorsphere_backend.Vendor.dto;

import com.vendorsphere.vendorsphere_backend.Vendor.entity.RiskLevel;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorResponseDto {

    private Long id;

    private String vendorCode;

    private String vendorName;

    private String email;

    private String phoneNumber;

    private String address;

    private String industry;

    private RiskLevel riskLevel;

    private Boolean active;
}