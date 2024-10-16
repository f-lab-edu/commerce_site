package org.example.commerce_site.representation.order.request;

import java.math.BigDecimal;
import java.util.List;

import org.example.commerce_site.application.order.dto.OrderRequestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class OrderRequest {
	@Getter
	public static class Create {
		@NotBlank(message = "total amount cannot be blank")
		private BigDecimal totalAmount;
		private List<CreateDetail> details;
		@NotNull
		private Long addressId;

		public static OrderRequestDto.Create toDto(OrderRequest.Create request, String userId) {
			return OrderRequestDto.Create.builder()
				.userAuthId(userId)
				.totalAmount(request.getTotalAmount())
				.details(CreateDetail.toDtos(request.getDetails()))
				.addressId(request.getAddressId())
				.build();
		}
	}

	@Getter
	public static class CreateDetail {
		@NotNull
		private Long productId;
		@NotNull
		private Long quantity;
		@NotNull
		private BigDecimal unitPrice;

		public static OrderRequestDto.CreateDetail toDto(CreateDetail request) {
			return OrderRequestDto.CreateDetail.builder()
				.productId(request.getProductId())
				.quantity(request.getQuantity())
				.unitPrice(request.getUnitPrice())
				.build();
		}

		public static List<OrderRequestDto.CreateDetail> toDtos(List<CreateDetail> requests) {
			return requests.stream().map(CreateDetail::toDto).toList();
		}
	}
}
