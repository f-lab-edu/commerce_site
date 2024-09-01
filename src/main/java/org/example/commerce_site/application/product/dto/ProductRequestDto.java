package org.example.commerce_site.application.product.dto;

import org.example.commerce_site.domain.Category;
import org.example.commerce_site.domain.Product;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class ProductRequestDto {
	@Getter
	@Builder
	@ToString
	public static class Create {
		private Long partnerId;
		private Long categoryId;
		private String name;
		private String description;
		private Long price;
		private Long stockQuantity;

		public static Product toEntity(ProductRequestDto.Create dto, Category category) {
			return Product.builder()
				.partnerId(dto.getPartnerId())
				.category(category)
				.name(dto.getName())
				.description(dto.getDescription())
				.price(dto.getPrice())
				.stockQuantity(dto.getStockQuantity())
				.isEnable(Boolean.TRUE)
				.build();
		}
	}

	@Getter
	@Builder
	@ToString
	public static class Put {
		private Long partnerId;
		private Long categoryId;
		private String name;
		private String description;
		private Long price;
		private Long stockQuantity;
		private Boolean isEnable;
	}
}
