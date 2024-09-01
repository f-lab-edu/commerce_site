package org.example.commerce_site.application.product.dto;

import java.time.LocalDateTime;

import org.example.commerce_site.domain.Product;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class ProductResponseDto {
	@Builder
	@Getter
	@ToString
	public static class Create {
		private Long id;
		private Long partnerId;
		private Long categoryId;
		private String name;
		private String description;
		private Long price;
		private Long stockQuantity;
		private LocalDateTime createdAt;

		public static ProductResponseDto.Create of(Product product) {
			return Create.builder()
				.id(product.getId())
				.partnerId(product.getPartnerId())
				.categoryId(product.getCategory().getId())
				.name(product.getName())
				.description(product.getDescription())
				.price(product.getPrice())
				.stockQuantity(product.getStockQuantity())
				.createdAt(product.getCreatedAt())
				.build();
		}
	}
}
