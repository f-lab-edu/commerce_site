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

	@Builder
	@Getter
	@ToString
	public static class Update {
		private Long id;
		private Long categoryId;
		private String name;
		private String description;
		private Long price;
		private Long stockQuantity;
		private Boolean isEnable;
		private LocalDateTime updatedAt;

		public static Update of(Product product) {
			return Update.builder()
				.id(product.getId())
				.categoryId(product.getCategory().getId())
				.name(product.getName())
				.description(product.getDescription())
				.price(product.getPrice())
				.stockQuantity(product.getStockQuantity())
				.isEnable(product.getIsEnable())
				.updatedAt(product.getUpdatedAt())
				.build();
		}
	}
}
