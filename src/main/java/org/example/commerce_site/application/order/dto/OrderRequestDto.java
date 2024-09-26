package org.example.commerce_site.application.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.example.commerce_site.attribute.OrderStatus;
import org.example.commerce_site.domain.Order;
import org.example.commerce_site.domain.OrderDetail;

import lombok.Builder;
import lombok.Getter;

public class OrderRequestDto {
	@Getter
	@Builder
	public static class Create {
		private Long userId;
		private BigDecimal totalAmount;
		private List<OrderRequestDto.CreateDetail> details;
		private Long addressId;

		public static Order toEntity(OrderRequestDto.Create dto) {
			return Order.builder()
				.userId(dto.getUserId())
				.totalAmount(dto.getTotalAmount())
				.status(OrderStatus.PENDING)
				.build();
		}
	}

	@Getter
	@Builder
	public static class CreateDetail {
		private Long productId;
		private Long quantity;
		private BigDecimal unitPrice;

		public static OrderDetail toEntity(OrderRequestDto.CreateDetail dto, Order order) {
			return OrderDetail.builder()
				.order(order)
				.productId(dto.getProductId())
				.unitPrice(dto.getUnitPrice())
				.quantity(dto.getQuantity())
				.createdAt(LocalDateTime.now(ZoneOffset.UTC))
				.build();
		}
	}
}
