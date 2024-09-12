package org.example.commerce_site.application.cart.dto;

import java.util.HashMap;
import java.util.Map;

import org.example.commerce_site.domain.Cart;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class CartRequestDto {
	@Getter
	@Builder
	@ToString
	public static class Create {
		private Long userId;
		private Long productId;
		private Long quantity;

		public static Cart toEntity(Create dto) {
			return Cart.builder()
				.userId(dto.getUserId())
				.productId(dto.getProductId())
				.quantity(dto.getQuantity())
				.build();
		}
	}

	@Getter
	@Builder
	@ToString
	public static class Update {
		private Long userId;
		private HashMap<Long, Long> productsMap;
	}
}
