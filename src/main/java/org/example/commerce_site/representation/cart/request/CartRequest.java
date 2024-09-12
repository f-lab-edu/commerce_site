package org.example.commerce_site.representation.cart.request;

import java.util.HashMap;
import java.util.List;

import org.example.commerce_site.application.cart.dto.CartRequestDto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

public class CartRequest {
	@Getter
	@ToString
	public static class Create {
		@NotNull(message = "Product ID cannot be null")
		private Long productId;

		@NotNull(message = "Quantity cannot be null")
		private Long quantity;

		public static CartRequestDto.Create toDto(CartRequest.Create request, Long userId) {
			return CartRequestDto.Create.builder()
				.userId(userId)
				.productId(request.getProductId())
				.quantity(request.getQuantity())
				.build();
		}
	}

	@Getter
	@ToString
	public static class Update {
		@NotNull
		private HashMap<Long, Long> productsMap;

		public static CartRequestDto.Update toDto(CartRequest.Update request, Long userId) {
			return CartRequestDto.Update.builder()
				.userId(userId)
				.productsMap(request.getProductsMap())
				.build();
		}
	}

	@Getter
	@ToString
	public static class Delete {
		@NotNull
		private List<Long> productIds;

		public static CartRequestDto.Delete toDto(CartRequest.Delete request, Long userId) {
			return CartRequestDto.Delete.builder()
				.userId(userId)
				.productIds(request.getProductIds())
				.build();
		}
	}
}
