package com.vendorsphere.vendorsphere_backend.Vendor.repository;

import com.vendorsphere.vendorsphere_backend.Vendor.entity.RiskLevel;
import com.vendorsphere.vendorsphere_backend.Vendor.entity.Vendor;
import org.springframework.data.jpa.domain.Specification;

import java.util.Locale;

public final class VendorSpecifications {

    private VendorSpecifications() {
    }

    public static Specification<Vendor> activeVendors() {
        return (root, query, builder) -> builder.isTrue(root.get("active"));
    }

    public static Specification<Vendor> matchesSearch(String search) {
        if (search == null || search.isBlank()) {
            return null;
        }

        String pattern = "%" + search.trim().toLowerCase(Locale.ROOT) + "%";
        return (root, query, builder) -> builder.or(
                builder.like(builder.lower(root.get("vendorName")), pattern),
                builder.like(builder.lower(root.get("vendorCode")), pattern),
                builder.like(builder.lower(root.get("email")), pattern),
                builder.like(builder.lower(root.get("industry")), pattern)
        );
    }

    public static Specification<Vendor> hasRiskLevel(RiskLevel riskLevel) {
        if (riskLevel == null) {
            return null;
        }
        return (root, query, builder) -> builder.equal(root.get("riskLevel"), riskLevel);
    }
}
