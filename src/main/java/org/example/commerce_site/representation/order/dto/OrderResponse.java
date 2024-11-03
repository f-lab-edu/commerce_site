package org.example.commerce_site.representation.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.example.commerce_site.application.order.dto.OrderDetailResponseDto;
import org.example.commerce_site.application.order.dto.OrderResponseDto;
import org.example.commerce_site.attribute.OrderStatus;
import org.example.commerce_site.attribute.ShipmentStatus;
import org.springframework.data.domain.Page;

import lombok.Builder;
import lombok.Getter;

public class OrderResponse {
	@Getter
	@Builder
	public static class Get {
		private Long id;
		private BigDecimal totalAmount;
		private OrderStatus status;
		private List<DetailGet> orderDetails;

		public static Get of(OrderResponseDto.Get dto) {
			return Get.builder()
				.id(dto.getId())
				.totalAmount(dto.getTotalAmount())
				.status(dto.getStatus())
				.orderDetails(DetailGet.of(dto.getOrderDetails()))
				.build();
		}

		public static Page<Get> of(Page<OrderResponseDto.Get> dtos) {
			return dtos.map(Get::of);
		}
	}

	@Getter
	@Builder
	public static class DetailGet {
		private LocalDateTime createdAt;
		private Long id;
		private Long productId;
		private Long quantity;
		private Long orderId;
		private BigDecimal unitPrice;
		private String productName;
		private ShipmentStatus shipmentStatus;
		private LocalDateTime shipmentCreatedAt;
		private LocalDateTime shipmentUpdatedAt;

		public static DetailGet of(OrderDetailResponseDto.GetList dto) {
			return DetailGet.builder()
				.createdAt(dto.getCreatedAt())
				.id(dto.getId())
				.productId(dto.getProductId())
				.quantity(dto.getQuantity())
				.orderId(dto.getOrderId())
				.unitPrice(dto.getUnitPrice())
				.productName(dto.getProductName())
				.shipmentStatus(dto.getShipmentStatus())
				.shipmentCreatedAt(dto.getShipmentCreatedAt())
				.shipmentUpdatedAt(dto.getShipmentUpdatedAt())
				.build();
		}

		public static List<DetailGet> of(List<OrderDetailResponseDto.GetList> dtos) {
			return dtos.stream().map(DetailGet::of).toList();
		}
	}
}
