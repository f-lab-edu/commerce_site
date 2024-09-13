package org.example.commerce_site.infrastructure.order;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

import org.example.commerce_site.application.order.dto.OrderDetailResponseDto;
import org.example.commerce_site.domain.OrderDetail;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderDetailBulkRepository {
	private final JdbcTemplate jdbcTemplate;

	@Transactional
	public List<OrderDetailResponseDto.Get> saveAll(List<OrderDetail> orderDetails, Long orderId) {
		String sql = "INSERT INTO order_details (order_id, product_id, quantity, unit_price, created_at) "
			+ "VALUES (?, ?, ?, ?, ?)";

		jdbcTemplate.batchUpdate(sql, orderDetails, orderDetails.size(),
			(PreparedStatement ps, OrderDetail orderDetail) -> {
				ps.setLong(1, orderDetail.getOrder().getId());
				ps.setLong(2, orderDetail.getProductId());
				ps.setLong(3, orderDetail.getQuantity());
				ps.setBigDecimal(4, orderDetail.getUnitPrice());
				ps.setTimestamp(5, Timestamp.valueOf(orderDetail.getCreatedAt()));
			});

		String selectSql = "SELECT id, product_id, quantity, unit_price, created_at FROM order_details WHERE order_id = ?";

		List<OrderDetailResponseDto.Get> updatedOrderDetails = jdbcTemplate.query(
			selectSql,
			new Object[] {orderId},
			(rs, rowNum) ->
				OrderDetailResponseDto.Get.builder()
					.id(rs.getLong("id"))
					.productId(rs.getLong("product_id"))
					.quantity(rs.getLong("quantity"))
					.unitPrice(rs.getBigDecimal("unit_price"))
					.orderId(orderId)
					.createdAt(rs.getTimestamp("created_at").toLocalDateTime())
					.build()
		);
		return updatedOrderDetails;
	}
}
