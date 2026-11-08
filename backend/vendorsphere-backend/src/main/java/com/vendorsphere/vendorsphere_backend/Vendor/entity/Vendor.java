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

    @Column(unique = true, nullable = false, length = 40)
    private String vendorCode;

    @Column(nullable = false, length = 120)
    private String vendorName;

    @Column(unique = true, nullable = false, length = 254)
    private String email;

    @Column(length = 25)
    private String phoneNumber;

    @Column(length = 300)
    private String address;

    @Column(length = 100)
    private String industry;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RiskLevel riskLevel;

    @Column(nullable = false)
    private Boolean active;

}
