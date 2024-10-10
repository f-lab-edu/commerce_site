package org.example.commerce_site.application.cart.dto;

import java.util.HashMap;
import java.util.List;

import org.example.commerce_site.domain.Cart;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class CartRequestDto {
	@Getter
	@Builder
	@ToString
	public static class Create {
		private Long userAuthId;
		private Long productId;
		private Long quantity;

		public static Cart toEntity(Create dto, Long userId) {
			return Cart.builder()
				.userId(userId)
				.userId(dto.getUserAuthId())
				.productId(dto.getProductId())
				.quantity(dto.getQuantity())
				.build();
		}
	}

	@Getter
	@Builder
	@ToString
	public static class Update {
		private String userAuthId;
		private HashMap<Long, Long> productsMap;
	}

	@Getter
	@Builder
	@ToString
	public static class Delete {
		private String userAuthId;
		private List<Long> productIds;
	}
}
