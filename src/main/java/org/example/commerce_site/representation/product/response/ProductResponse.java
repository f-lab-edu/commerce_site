package org.example.commerce_site.representation.product.response;

import java.time.LocalDateTime;

import org.example.commerce_site.application.product.dto.ProductResponseDto;
import org.springframework.data.domain.Page;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class ProductResponse {
	@Getter
	@Builder
	@ToString
	public static class Get {
		private Long id;
		private Long partnerId;
		private String partnerName;
		private Long categoryId;
		private String categoryName;
		private String name;
		private String description;
		private Long price;
		private Long stockQuantity;
		private LocalDateTime createdAt;

		public static Get of(ProductResponseDto.Get dto) {
			return ProductResponse.Get.builder()
				.id(dto.getId())
				.partnerId(dto.getPartnerId())
				.partnerName(dto.getPartnerName())
				.categoryId(dto.getCategoryId())
				.categoryName(dto.getCategoryName())
				.name(dto.getName())
				.description(dto.getDescription())
				.price(dto.getPrice())
				.stockQuantity(dto.getStockQuantity())
				.createdAt(dto.getCreatedAt())
				.build();
		}

		public static Page<Get> of(Page<ProductResponseDto.Get> dtos) {
			return dtos.map(ProductResponse.Get::of);
		}
	}
}
