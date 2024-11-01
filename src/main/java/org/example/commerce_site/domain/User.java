package org.example.commerce_site.domain;

import org.example.commerce_site.attribute.UserStatus;
import org.example.commerce_site.common.domain.Account;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends Account {
	@Enumerated(EnumType.STRING)
	private UserStatus status;
}
