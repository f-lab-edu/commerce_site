package org.example.commerce_site.domain;

import org.example.commerce_site.common.domain.BaseTimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "carts")
public class Cart extends BaseTimeEntity {
	private Long userId;
	private Long productId;
	private Long quantity;

	public void addQuantity(Long quantity) {
		this.quantity += quantity;
	}

	public void updateQuantity(Long quantity) {
		this.quantity = quantity;
	}
}
