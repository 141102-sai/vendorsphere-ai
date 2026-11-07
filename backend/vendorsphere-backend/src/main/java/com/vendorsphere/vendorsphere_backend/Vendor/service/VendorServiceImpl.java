package com.vendorsphere.vendorsphere_backend.Vendor.service;

import com.vendorsphere.vendorsphere_backend.Vendor.dto.VendorRequestDto;
import com.vendorsphere.vendorsphere_backend.Vendor.dto.VendorResponseDto;
import com.vendorsphere.vendorsphere_backend.Vendor.entity.RiskLevel;
import com.vendorsphere.vendorsphere_backend.Vendor.entity.Vendor;
import com.vendorsphere.vendorsphere_backend.Vendor.exception.VendorAlreadyExistsException;
import com.vendorsphere.vendorsphere_backend.Vendor.exception.VendorNotFoundException;
import com.vendorsphere.vendorsphere_backend.Vendor.mapper.VendorMapper;
import com.vendorsphere.vendorsphere_backend.Vendor.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class VendorServiceImpl implements VendorService {
    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;

    @Override
    public VendorResponseDto createVendor(
            VendorRequestDto request
    ) {

        if (vendorRepository
                .findByEmail(request.getEmail())
                .isPresent()) {

            throw new VendorAlreadyExistsException(
                    "Vendor already exists with email : "
                            + request.getEmail()
            );
        }

        Vendor vendor = vendorMapper.toEntity(request);

        vendor.setVendorCode(generateVendorCode());

        vendor.setRiskLevel(RiskLevel.LOW);
        vendor.setActive(true);
        Vendor savedVendor = vendorRepository.save(vendor);
        return vendorMapper.toResponseDto(savedVendor);
    }

    @Override
    public VendorResponseDto getVendorById(Long id) {
        Vendor vendor =
                vendorRepository.findById(id)
                        .orElseThrow(() -> new VendorNotFoundException("Vendor not found with id : " + id)
                        );

        return vendorMapper.toResponseDto(vendor);
    }

    @Override
    public List<VendorResponseDto>
    getAllVendors() {
        return vendorRepository.findByActiveTrue()
                .stream()
                .map(vendorMapper::toResponseDto)
                .toList();
    }

    private String generateVendorCode() {
        return "VENDOR-"
                + System.currentTimeMillis();
    }

    @Override
    public VendorResponseDto updateVendor(Long id, VendorRequestDto request) {
        Vendor vendor = vendorRepository.findById(id)
                        .orElseThrow(() -> new VendorNotFoundException("Vendor not found with id : " + id)
                        );
        vendor.setVendorName(request.getVendorName());
        vendor.setEmail(request.getEmail());
        vendor.setPhoneNumber(request.getPhoneNumber());
        vendor.setAddress(request.getAddress());
        vendor.setIndustry(request.getIndustry());
        Vendor updatedVendor = vendorRepository.save(vendor);
        return vendorMapper
                .toResponseDto(updatedVendor);
    }

    public void deleteVendor(Long id) {
        Vendor vendor = vendorRepository.findById(id)
                        .orElseThrow(() -> new VendorNotFoundException("Vendor not found"));
        vendor.setActive(false);
        vendorRepository.save(vendor);
    }

    @Override
    public Page<VendorResponseDto> getAllVendors(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Vendor> vendorPage = vendorRepository.findAll(pageable);
        return vendorPage
                .map(vendorMapper::toResponseDto);
    }

    @Override
    public Page<VendorResponseDto> getAllVendors(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        Page<Vendor> vendorPage = vendorRepository.findAll(pageable);
        return vendorPage
                .map(vendorMapper::toResponseDto);
    }

    @Override
    public List<VendorResponseDto> searchVendor(String vendorName) {
        return vendorRepository
                .findByVendorNameContainingIgnoreCase(vendorName)
                .stream()
                .map(vendorMapper::toResponseDto)
                .toList();
    }

    @Override
    public List<VendorResponseDto> filterByRiskLevel(RiskLevel riskLevel) {
        return vendorRepository
                .findByRiskLevel(riskLevel)
                .stream()
                .map(vendorMapper::toResponseDto)
                .toList();
    }

}
