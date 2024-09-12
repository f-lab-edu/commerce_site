package org.example.commerce_site.representation.cart.request;

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
}
