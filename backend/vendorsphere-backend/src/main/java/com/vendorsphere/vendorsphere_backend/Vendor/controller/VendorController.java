package com.vendorsphere.vendorsphere_backend.Vendor.controller;

import com.vendorsphere.vendorsphere_backend.Vendor.dto.VendorRequestDto;
import com.vendorsphere.vendorsphere_backend.Vendor.dto.VendorResponseDto;
import com.vendorsphere.vendorsphere_backend.Vendor.entity.RiskLevel;
import com.vendorsphere.vendorsphere_backend.Vendor.service.VendorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendors")
@RequiredArgsConstructor
public class VendorController {

    private final VendorService vendorService;

    @PostMapping
    public ResponseEntity<VendorResponseDto> createVendor(@Valid @RequestBody VendorRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vendorService.createVendor(request));
    }

    @GetMapping
    public ResponseEntity<Page<VendorResponseDto>> searchVendors(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) RiskLevel riskLevel,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "vendorName") String sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction) {
        return ResponseEntity.ok(vendorService.searchVendors(search, riskLevel, page, size, sortBy, direction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendorResponseDto> getVendorById(@PathVariable Long id) {
        return ResponseEntity.ok(vendorService.getVendorById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VendorResponseDto> updateVendor(
            @PathVariable Long id,
            @Valid @RequestBody VendorRequestDto request) {
        return ResponseEntity.ok(vendorService.updateVendor(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVendor(@PathVariable Long id) {
        vendorService.deleteVendor(id);
        return ResponseEntity.noContent().build();
    }
}
