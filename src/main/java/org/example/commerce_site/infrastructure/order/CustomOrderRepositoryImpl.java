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
import org.example.commerce_site.common.domain.Account;
import org.example.commerce_site.common.util.PageConverter;
import org.example.commerce_site.domain.Partner;
import org.example.commerce_site.domain.QOrder;
import org.example.commerce_site.domain.QOrderDetail;
import org.example.commerce_site.domain.User;
import org.flywaydb.core.internal.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

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

	private final JPAQueryFactory queryFactory;

	@Override
	public <T extends Account> Page<OrderResponseDto.Get> getOrders(Pageable pageable, String keyword, T user) {
		boolean isPartner = false;

		if (user instanceof Partner) {
			isPartner = true;
		}

		StringBuilder sql = new StringBuilder("SELECT o.id, o.total_amount, o.status, " +
			"od.id AS order_detail_id, od.created_at, od.product_id, od.quantity, " +
			"od.order_id, od.unit_price, p.name AS product_name, s.status AS shipment_status, " +
			"s.created_at AS shipment_created_at, s.updated_at AS shipment_updated_at, " +
			"a.phone_number AS phone_number, a.postal_code AS postal_code, " +
			"a.road_address AS road_address, a.jibun_address AS jibun_address, " +
			"a.address_detail AS address_detail " +
			"FROM orders o " +
			"INNER JOIN order_details od ON o.id = od.order_id " +
			"LEFT JOIN products p ON od.product_id = p.id " +
			"LEFT JOIN shipments s ON od.id = s.order_detail_id " +
			"LEFT JOIN addresses a ON s.address_id = a.id "
		);

		if (isPartner) {
			sql.append("WHERE p.partner_id = :partnerId ");
		} else {
			sql.append("WHERE o.user_id = :userId ");
		}

		if (StringUtils.hasText(keyword)) {
			sql.append("AND p.name IS NOT NULL AND (MATCH(p.name) AGAINST (:keyword IN BOOLEAN MODE) " +
				"OR p.name LIKE CONCAT(:keyword, '%')) ");
		}

		sql.append("ORDER BY o.created_at LIMIT :pageSize OFFSET :offset");

		Query query = entityManager.createNativeQuery(sql.toString());

		if (isPartner) {
			query.setParameter("partnerId", user.getId());
		} else {
			query.setParameter("userId", user.getId());
		}

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
				convertTimestampToLocalDateTime((Timestamp)row[12]),  // shipmentUpdatedAt
				(String)row[13],
				(String)row[14],
				(String)row[15],
				(String)row[16],
				(String)row[17]
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
	public List<OrderResponseDto.GetOneOff> getOrderListByUserIdAndProductId(User user, Long productId) {
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(QOrder.order.userId.eq(user.getId()));
		builder.and(QOrderDetail.orderDetail.productId.eq(productId));

		return queryFactory.select(Projections.constructor(OrderResponseDto.GetOneOff.class,
				QOrder.order.id,
				QOrder.order.totalAmount,
				QOrder.order.status
			))
			.from(QOrder.order)
			.leftJoin(QOrderDetail.orderDetail)
			.on(QOrderDetail.orderDetail.order.id.eq(QOrder.order.id))
			.where(builder)
			.fetch();
	}

	private LocalDateTime convertTimestampToLocalDateTime(Timestamp timestamp) {
		return timestamp != null ? timestamp.toLocalDateTime() : null;
	}
}
