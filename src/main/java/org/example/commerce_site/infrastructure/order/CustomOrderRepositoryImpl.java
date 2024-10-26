package org.example.commerce_site.infrastructure.order;

import java.util.List;
import java.util.stream.Collectors;

import org.example.commerce_site.application.order.dto.OrderDetailResponseDto;
import org.example.commerce_site.application.order.dto.OrderResponseDto;
import org.example.commerce_site.common.util.PageConverter;
import org.example.commerce_site.domain.QOrder;
import org.example.commerce_site.domain.QOrderDetail;
import org.example.commerce_site.domain.QProduct;
import org.example.commerce_site.domain.QShipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CustomOrderRepositoryImpl implements CustomOrderRepository {
	private final JPAQueryFactory queryFactory;

	@Override
	public Page<OrderResponseDto.Get> getOrders(Pageable pageable, String keyword, Long userId) {
		BooleanBuilder builder = new BooleanBuilder();

		if (StringUtils.hasText(keyword)) {
			builder.and(QProduct.product.name.contains(keyword));
		}

		List<OrderResponseDto.Get> orderList = queryFactory.select(Projections.constructor(OrderResponseDto.Get.class,
				QOrder.order.id,
				QOrder.order.totalAmount,
				QOrder.order.status,
				Projections.list(Projections.constructor(OrderDetailResponseDto.Get.class,
					QOrderDetail.orderDetail.createdAt,
					QOrderDetail.orderDetail.id,
					QOrderDetail.orderDetail.productId,
					QOrderDetail.orderDetail.quantity,
					QOrderDetail.orderDetail.order.id,
					QOrderDetail.orderDetail.unitPrice,
					QProduct.product.name,
					QShipment.shipment.status,
					QShipment.shipment.createdAt,
					QShipment.shipment.updatedAt
				))
			))
			.from(QOrder.order)
			.leftJoin(QOrderDetail.orderDetail).on(QOrder.order.id.eq(QOrderDetail.orderDetail.order.id))
			.leftJoin(QProduct.product).on(QOrderDetail.orderDetail.productId.eq(QProduct.product.id))
			.leftJoin(QShipment.shipment).on(QOrderDetail.orderDetail.id.eq(QShipment.shipment.orderDetail.id))
			.where(QOrder.order.userId.eq(userId).and(builder))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		List<OrderResponseDto.Get> groupedOrderList = orderList.stream()
			.collect(Collectors.groupingBy(OrderResponseDto.Get::getId))
			.values()
			.stream()
			.map(orderGroup -> {
				OrderResponseDto.Get firstOrder = orderGroup.get(0);
				List<OrderDetailResponseDto.Get> allDetails = orderGroup.stream()
					.flatMap(order -> order.getOrderDetails().stream())
					.collect(Collectors.toList());
				firstOrder.setOrderDetails(allDetails);
				return firstOrder;
			})
			.collect(Collectors.toList());

		return PageConverter.getPage(groupedOrderList, pageable);
	}
}
