package com.vendorsphere.vendorsphere_backend.Vendor.service;

import com.vendorsphere.vendorsphere_backend.Vendor.dto.VendorRequestDto;
import com.vendorsphere.vendorsphere_backend.Vendor.dto.VendorResponseDto;
import com.vendorsphere.vendorsphere_backend.Vendor.entity.RiskLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface VendorService {

    VendorResponseDto createVendor(VendorRequestDto request);

    VendorResponseDto getVendorById(Long id);

    Page<VendorResponseDto> searchVendors(
            String search,
            RiskLevel riskLevel,
            int page,
            int size,
            String sortBy,
            Sort.Direction direction);

    VendorResponseDto updateVendor(Long id, VendorRequestDto request);

    void deleteVendor(Long id);
}
