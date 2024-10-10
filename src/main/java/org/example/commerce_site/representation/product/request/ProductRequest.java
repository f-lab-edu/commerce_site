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
		private Long categoryId;
		@NotBlank
		private String name;
		private String description;
		@NotNull
		private Long price;
		@NotNull
		private Long stockQuantity;

		public static ProductRequestDto.Create toDTO(ProductRequest.Create request, String partnerAuthId) {
			return ProductRequestDto.Create.builder()
				.partnerId(partnerAuthId)
				.categoryId(request.getCategoryId())
				.name(request.getName())
				.description(request.getDescription())
				.price(request.getPrice())
				.stockQuantity(request.getStockQuantity())
				.build();
		}
	}

	@Getter
	@ToString
	public static class Update {
		private Long categoryId;
		private String name;
		private String description;
		private Long price;
		private Long stockQuantity;
		private Boolean isEnable;

		public static ProductRequestDto.Put toDTO(Update request, String partnerAuthId) {
			return ProductRequestDto.Put.builder()
				.partnerId(partnerAuthId)
				.categoryId(request.getCategoryId())
				.name(request.getName())
				.description(request.getDescription())
				.price(request.getPrice())
				.stockQuantity(request.getStockQuantity())
				.isEnable(request.getIsEnable())
				.build();
		}
	}
}
