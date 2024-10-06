package org.example.commerce_site.infrastructure.order;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

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
	public void saveAll(List<OrderDetail> orderDetails, Long orderId) {
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
	}
}
