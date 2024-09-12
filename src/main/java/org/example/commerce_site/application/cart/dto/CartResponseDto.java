package org.example.commerce_site.application.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class CartResponseDto {
	@Builder
	@Getter
	@ToString
	@AllArgsConstructor
	public static class Get {
		private Long id;
		private Long productId;
		private String productName;
		private Long categoryId;
		private String categoryName;
		private Long price;
		private Long quantity;
		private Long partnerId;
	}
}
