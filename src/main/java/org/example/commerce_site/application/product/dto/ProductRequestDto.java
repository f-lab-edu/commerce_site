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
		private String partnerId;
		private Long categoryId;
		private String name;
		private String description;
		private Long price;
		private Long stockQuantity;

		public static Product toEntity(ProductRequestDto.Create dto, Category category, Long partnerId) {
			return Product.builder()
				.partnerId(partnerId)
				.category(category)
				.name(dto.getName())
				.description(dto.getDescription())
				.price(dto.getPrice())
				.stockQuantity(dto.getStockQuantity())
				.isEnable(Boolean.TRUE)
				.isDeleted(Boolean.FALSE)
				.build();
		}
	}

	@Getter
	@Builder
	@ToString
	public static class Put {
		private String partnerAuthId;
		private Long categoryId;
		private String name;
		private String description;
		private Long price;
		private Long stockQuantity;
		private Boolean isEnable;
	}
}
