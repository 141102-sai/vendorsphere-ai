package com.vendorsphere.vendorsphere_backend.Vendor.service;

import com.vendorsphere.vendorsphere_backend.Vendor.dto.VendorRequestDto;
import com.vendorsphere.vendorsphere_backend.Vendor.dto.VendorResponseDto;
import com.vendorsphere.vendorsphere_backend.Vendor.entity.RiskLevel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface VendorService {
    VendorResponseDto createVendor(VendorRequestDto request);
    VendorResponseDto getVendorById(Long id);
    List<VendorResponseDto> getAllVendors();
    VendorResponseDto updateVendor(Long id, VendorRequestDto request);
    void deleteVendor(Long id);
    List<VendorResponseDto> searchVendor(String vendorName);
    Page<VendorResponseDto> getAllVendors(int page, int size);
    Page<VendorResponseDto> getAllVendors(int page, int size, String sortBy);
    List<VendorResponseDto> filterByRiskLevel(RiskLevel riskLevel);

}
