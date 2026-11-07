package com.vendorsphere.vendorsphere_backend.Vendor.repository;

import com.vendorsphere.vendorsphere_backend.Vendor.entity.RiskLevel;
import com.vendorsphere.vendorsphere_backend.Vendor.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VendorRepository extends JpaRepository<Vendor, Long> {

    Optional<Vendor> findByVendorCode(String vendorCode);
    Optional<Vendor> findByEmail(String email);
    List<Vendor> findByActiveTrue();
    List<Vendor> findByVendorNameContainingIgnoreCase(String vendorName);
    List<Vendor> findByRiskLevel(RiskLevel riskLevel);

}
