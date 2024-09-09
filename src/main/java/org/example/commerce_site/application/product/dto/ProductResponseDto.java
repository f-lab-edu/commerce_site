package org.example.commerce_site.application.product.dto;

import java.time.LocalDateTime;

import org.example.commerce_site.domain.Partner;
import org.example.commerce_site.domain.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class ProductResponseDto {
	@Builder
	@Getter
	@ToString
	@AllArgsConstructor
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

		public static Get of(Product product, Partner partner) {
			return Get.builder()
				.id(product.getId())
				.partnerId(product.getPartnerId())
				.partnerName(partner.getName())
				.categoryId(product.getCategory().getId())
				.categoryName(product.getCategory().getName())
				.name(product.getName())
				.description(product.getDescription())
				.price(product.getPrice())
				.stockQuantity(product.getStockQuantity())
				.createdAt(product.getCreatedAt())
				.build();
		}
	}
}
