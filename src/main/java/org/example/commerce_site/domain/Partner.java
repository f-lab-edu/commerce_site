package org.example.commerce_site.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.commerce_site.attribute.PartnerStatus;
import org.example.commerce_site.common.domain.BaseTimeEntity;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "partners")
public class Partner extends BaseTimeEntity {
    private String name;
    private String businessNumber;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    private PartnerStatus status;
}
