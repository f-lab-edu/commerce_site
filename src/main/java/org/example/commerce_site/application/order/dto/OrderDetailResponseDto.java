package org.example.commerce_site.application.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.example.commerce_site.domain.OrderDetail;

import lombok.Builder;
import lombok.Getter;

public class OrderDetailResponseDto {
	@Getter
	@Builder
	public static class Get {
		private LocalDateTime createdAt;
		private Long id;
		private Long productId;
		private Long quantity;
		private BigDecimal unitPrice;

		public static OrderDetailResponseDto.Get toDto(OrderDetail orderDetail) {
			return Get.builder()
				.id(orderDetail.getId())
				.productId(orderDetail.getProductId())
				.unitPrice(orderDetail.getUnitPrice())
				.quantity(orderDetail.getQuantity())
				.createdAt(orderDetail.getCreatedAt())
				.build();
		}

		public static List<OrderDetailResponseDto.Get> toDtoList(List<OrderDetail> orderDetails) {
			return orderDetails.stream().map(Get::toDto).toList();
		}
	}
}
