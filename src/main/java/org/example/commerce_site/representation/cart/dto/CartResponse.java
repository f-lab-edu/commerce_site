package org.example.commerce_site.representation.cart.dto;

import org.example.commerce_site.application.cart.dto.CartResponseDto;
import org.springframework.data.domain.Page;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class CartResponse {
	@Getter
	@Builder
	@ToString
	public static class Get {
		private Long id;
		private Long productId;
		private String productName;
		private Long categoryId;
		private String categoryName;
		private Long price;
		private Long quantity;
		private Long partnerId;

		public static CartResponse.Get of(CartResponseDto.Get dto) {
			return CartResponse.Get.builder()
				.id(dto.getId())
				.productId(dto.getProductId())
				.productName(dto.getProductName())
				.categoryId(dto.getCategoryId())
				.categoryName(dto.getCategoryName())
				.price(dto.getPrice())
				.quantity(dto.getQuantity())
				.partnerId(dto.getPartnerId())
				.build();
		}

		public static Page<CartResponse.Get> of(Page<CartResponseDto.Get> dtos) {
			return dtos.map(CartResponse.Get::of);
		}
	}
}
