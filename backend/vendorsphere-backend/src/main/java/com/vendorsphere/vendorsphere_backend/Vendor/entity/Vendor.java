package com.vendorsphere.vendorsphere_backend.Vendor.entity;

import com.vendorsphere.vendorsphere_backend.Auth.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="vendors")
public class Vendor extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            unique = true,
            nullable = false
    )
    private String vendorCode;

    @Column(nullable = false)
    private String vendorName;

    @Column(
            unique = true,
            nullable = false
    )
    private String email;

    private String phoneNumber;

    private String address;

    private String industry;

    @Enumerated(EnumType.STRING)
    private RiskLevel riskLevel;

    private Boolean active;

}
