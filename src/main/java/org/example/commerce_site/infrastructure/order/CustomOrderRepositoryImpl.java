package org.example.commerce_site.infrastructure.order;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.example.commerce_site.application.order.dto.OrderDetailResponseDto;
import org.example.commerce_site.application.order.dto.OrderResponseDto;
import org.example.commerce_site.attribute.OrderStatus;
import org.example.commerce_site.attribute.ShipmentStatus;
import org.example.commerce_site.common.util.PageConverter;
import org.example.commerce_site.domain.User;
import org.flywaydb.core.internal.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CustomOrderRepositoryImpl implements CustomOrderRepository {
	@PersistenceContext
	private final EntityManager entityManager;

	@Override
	public Page<OrderResponseDto.Get> getOrders(Pageable pageable, String keyword, Long userId) {
		StringBuilder sql = new StringBuilder("SELECT o.id, o.total_amount, o.status, " +
			"od.id AS order_detail_id, od.created_at, od.product_id, od.quantity, " +
			"od.order_id, od.unit_price, p.name AS product_name, s.status AS shipment_status, " +
			"s.created_at AS shipment_created_at, s.updated_at AS shipment_updated_at " +
			"FROM orders o " +
			"INNER JOIN order_details od ON o.id = od.order_id " +
			"LEFT JOIN products p ON od.product_id = p.id " +
			"LEFT JOIN shipments s ON od.id = s.order_detail_id " +
			"WHERE o.user_id = :userId ");

		if (StringUtils.hasText(keyword)) {
			sql.append("AND p.name IS NOT NULL AND (MATCH(p.name) AGAINST (:keyword IN BOOLEAN MODE) " +
				"OR p.name LIKE CONCAT(:keyword, '%')) ");
		}

		sql.append("ORDER BY o.created_at LIMIT :pageSize OFFSET :offset");

		Query query = entityManager.createNativeQuery(sql.toString());
		query.setParameter("userId", userId);
		query.setParameter("pageSize", pageable.getPageSize());
		query.setParameter("offset", pageable.getOffset());

		if (StringUtils.hasText(keyword)) {
			query.setParameter("keyword", keyword);
		}

		List<Object[]> resultList = query.getResultList();

		Map<Long, OrderResponseDto.Get> orderMap = new HashMap<>();
		for (Object[] row : resultList) {
			Long orderId = (Long)row[0]; // 주문 ID
			BigDecimal totalAmount = BigDecimal.valueOf((Long)row[1]); // 총 금액
			OrderStatus orderStatus = OrderStatus.valueOf((String)row[2]); // 주문 상태

			OrderDetailResponseDto.GetList orderDetail = new OrderDetailResponseDto.GetList(
				(Long)row[3], // orderDetailId
				convertTimestampToLocalDateTime((Timestamp)row[4]), // createdAt
				(Long)row[5], // productId
				(Long)row[6], // quantity
				(Long)row[7], // orderId
				BigDecimal.valueOf((Long)row[8]), // unitPrice
				(String)row[9], // productName
				ShipmentStatus.valueOf((String)row[10]), // shipmentStatus
				convertTimestampToLocalDateTime((Timestamp)row[11]), // shipmentCreatedAt
				convertTimestampToLocalDateTime((Timestamp)row[12])  // shipmentUpdatedAt
			);

			OrderResponseDto.Get orderResponse = orderMap.get(orderId);
			if (orderResponse == null) {
				orderResponse = new OrderResponseDto.Get(orderId, totalAmount, orderStatus, new ArrayList<>());
				orderMap.put(orderId, orderResponse);
			}

			orderResponse.getOrderDetails().add(orderDetail);
		}

		List<OrderResponseDto.Get> orderList = new ArrayList<>(orderMap.values());

		return PageConverter.getPage(orderList, pageable);
	}

	@Override
	public boolean isOrderExists(User user, Long productId) {
		StringBuilder sql = new StringBuilder("SELECT * " +
			"FROM orders o " +
			"INNER JOIN order_details od ON o.id = od.order_id " +
			"LEFT JOIN products p ON od.product_id = p.id " +
			"LEFT JOIN shipments s ON od.id = s.order_detail_id " +
			"WHERE o.user_id = :userId AND p.id = :productId " +
			"AND s.status = :status");

		Query query = entityManager.createNativeQuery(sql.toString());
		query.setParameter("userId", user.getId());
		query.setParameter("productId", productId);
		query.setParameter("status", ShipmentStatus.DELIVERED.name());

		if (query.getResultList().isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	private LocalDateTime convertTimestampToLocalDateTime(Timestamp timestamp) {
		return timestamp != null ? timestamp.toLocalDateTime() : null;
	}
}
