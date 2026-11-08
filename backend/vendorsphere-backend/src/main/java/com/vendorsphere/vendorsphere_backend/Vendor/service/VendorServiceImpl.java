package com.vendorsphere.vendorsphere_backend.Vendor.service;

import com.vendorsphere.vendorsphere_backend.Vendor.dto.VendorRequestDto;
import com.vendorsphere.vendorsphere_backend.Vendor.dto.VendorResponseDto;
import com.vendorsphere.vendorsphere_backend.Vendor.entity.RiskLevel;
import com.vendorsphere.vendorsphere_backend.Vendor.entity.Vendor;
import com.vendorsphere.vendorsphere_backend.Vendor.exception.VendorAlreadyExistsException;
import com.vendorsphere.vendorsphere_backend.Vendor.exception.VendorNotFoundException;
import com.vendorsphere.vendorsphere_backend.Vendor.mapper.VendorMapper;
import com.vendorsphere.vendorsphere_backend.Vendor.repository.VendorRepository;
import com.vendorsphere.vendorsphere_backend.Vendor.repository.VendorSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class VendorServiceImpl implements VendorService {

    private static final int MAX_PAGE_SIZE = 100;
    private static final Set<String> SORTABLE_FIELDS = Set.of(
            "vendorName", "vendorCode", "email", "industry", "riskLevel");

    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;

    @Override
    public VendorResponseDto createVendor(VendorRequestDto request) {
        String normalizedEmail = request.getEmail().trim();
        if (vendorRepository.findByEmailIgnoreCase(normalizedEmail).isPresent()) {
            throw new VendorAlreadyExistsException("A vendor already exists with this email address");
        }

        Vendor vendor = vendorMapper.toEntity(request);
        vendor.setVendorCode(generateVendorCode());
        vendor.setRiskLevel(RiskLevel.LOW);
        vendor.setActive(true);

        return vendorMapper.toResponseDto(vendorRepository.save(vendor));
    }

    @Override
    @Transactional(readOnly = true)
    public VendorResponseDto getVendorById(Long id) {
        Vendor vendor = vendorRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new VendorNotFoundException("Active vendor not found with id: " + id));
        return vendorMapper.toResponseDto(vendor);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VendorResponseDto> searchVendors(
            String search,
            RiskLevel riskLevel,
            int page,
            int size,
            String sortBy,
            Sort.Direction direction) {

        if (page < 0) {
            throw new IllegalArgumentException("Page index cannot be negative");
        }
        if (size < 1 || size > MAX_PAGE_SIZE) {
            throw new IllegalArgumentException("Page size must be between 1 and " + MAX_PAGE_SIZE);
        }
        if (!SORTABLE_FIELDS.contains(sortBy)) {
            throw new IllegalArgumentException("Unsupported sort field: " + sortBy);
        }

        Specification<Vendor> specification = VendorSpecifications.activeVendors();
        if (search != null && !search.isBlank()) {
            specification = specification.and(VendorSpecifications.matchesSearch(search));
        }
        if (riskLevel != null) {
            specification = specification.and(VendorSpecifications.hasRiskLevel(riskLevel));
        }

        Sort sort = Sort.by(direction, sortBy).and(Sort.by(Sort.Direction.ASC, "id"));
        Pageable pageable = PageRequest.of(page, size, sort);
        return vendorRepository.findAll(specification, pageable).map(vendorMapper::toResponseDto);
    }

    @Override
    public VendorResponseDto updateVendor(Long id, VendorRequestDto request) {
        Vendor vendor = vendorRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new VendorNotFoundException("Active vendor not found with id: " + id));

        String normalizedEmail = request.getEmail().trim();
        if (vendorRepository.existsByEmailIgnoreCaseAndIdNot(normalizedEmail, id)) {
            throw new VendorAlreadyExistsException("A vendor already exists with this email address");
        }

        vendorMapper.updateFromRequest(request, vendor);
        return vendorMapper.toResponseDto(vendorRepository.save(vendor));
    }

    @Override
    public void deleteVendor(Long id) {
        Vendor vendor = vendorRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new VendorNotFoundException("Active vendor not found with id: " + id));
        vendor.setActive(false);
        vendorRepository.save(vendor);
    }

    private String generateVendorCode() {
        return "VENDOR-" + UUID.randomUUID().toString().replace("-", "").toUpperCase(Locale.ROOT);
    }
}
