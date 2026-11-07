package com.vendorsphere.vendorsphere_backend.Vendor.mapper;

import com.vendorsphere.vendorsphere_backend.Vendor.dto.VendorRequestDto;
import com.vendorsphere.vendorsphere_backend.Vendor.dto.VendorResponseDto;
import com.vendorsphere.vendorsphere_backend.Vendor.entity.Vendor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VendorMapper {
    Vendor toEntity(VendorRequestDto dto);
    VendorResponseDto toResponseDto(Vendor vendor);
}
