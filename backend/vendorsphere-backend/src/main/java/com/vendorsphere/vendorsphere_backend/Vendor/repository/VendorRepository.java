package com.vendorsphere.vendorsphere_backend.Vendor.repository;

import com.vendorsphere.vendorsphere_backend.Vendor.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface VendorRepository extends JpaRepository<Vendor, Long>, JpaSpecificationExecutor<Vendor> {

    Optional<Vendor> findByVendorCode(String vendorCode);
    Optional<Vendor> findByEmailIgnoreCase(String email);
    Optional<Vendor> findByIdAndActiveTrue(Long id);
    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);

}
