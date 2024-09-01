package org.example.commerce_site.representation.product.response;

import java.time.LocalDateTime;

import org.example.commerce_site.application.product.dto.ProductResponseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class ProductResponse {
	@Getter
	@Builder
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

		public static ProductResponse.Create of(ProductResponseDto.Create dto) {
			return ProductResponse.Create.builder()
				.id(dto.getId())
				.partnerId(dto.getPartnerId())
				.categoryId(dto.getCategoryId())
				.name(dto.getName())
				.description(dto.getDescription())
				.price(dto.getPrice())
				.stockQuantity(dto.getStockQuantity())
				.createdAt(dto.getCreatedAt())
				.build();
		}
	}
}
