package org.example.commerce_site.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.commerce_site.attribute.UserStatus;
import org.example.commerce_site.common.domain.BaseTimeEntity;

@Entity
@Getter
@SuperBuilder
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
