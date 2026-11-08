package com.vendorsphere.vendorsphere_backend.Vendor.mapper;

import com.vendorsphere.vendorsphere_backend.Vendor.dto.VendorRequestDto;
import com.vendorsphere.vendorsphere_backend.Vendor.dto.VendorResponseDto;
import com.vendorsphere.vendorsphere_backend.Vendor.entity.Vendor;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface VendorMapper {
    Vendor toEntity(VendorRequestDto dto);
    VendorResponseDto toResponseDto(Vendor vendor);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromRequest(VendorRequestDto dto, @MappingTarget Vendor vendor);

    @AfterMapping
    default void normalizeVendorFields(@MappingTarget Vendor vendor) {
        if (vendor.getVendorName() != null) {
            vendor.setVendorName(vendor.getVendorName().trim());
        }
        if (vendor.getEmail() != null) {
            vendor.setEmail(vendor.getEmail().trim().toLowerCase(java.util.Locale.ROOT));
        }
        if (vendor.getPhoneNumber() != null) {
            vendor.setPhoneNumber(vendor.getPhoneNumber().trim());
        }
        if (vendor.getAddress() != null) {
            vendor.setAddress(vendor.getAddress().trim());
        }
        if (vendor.getIndustry() != null) {
            vendor.setIndustry(vendor.getIndustry().trim());
        }
    }
}
