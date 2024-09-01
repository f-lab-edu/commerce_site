package org.example.commerce_site.representation.product.request;

import org.example.commerce_site.application.product.dto.ProductRequestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

public class ProductRequest {
	@Getter
	@ToString
	public static class Create {
		@NotNull
		private Long partnerId;

		@NotNull
		private Long categoryId;

		@NotBlank
		private String name;

		private String description;

		@NotNull
		private Long price;

		@NotNull
		private Long stockQuantity;

		public static ProductRequestDto.Create toDTO(ProductRequest.Create request) {

			return ProductRequestDto.Create.builder()
				.partnerId(request.getPartnerId())
				.categoryId(request.getCategoryId())
				.name(request.getName())
				.description(request.getDescription())
				.price(request.getPrice())
				.stockQuantity(request.getStockQuantity())
				.build();
		}
	}
}
