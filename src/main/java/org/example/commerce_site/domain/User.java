package org.example.commerce_site.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.commerce_site.attribute.UserStatus;
import org.example.commerce_site.common.domain.BaseTimeEntity;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseTimeEntity {
    private String name;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserStatus status;
}
