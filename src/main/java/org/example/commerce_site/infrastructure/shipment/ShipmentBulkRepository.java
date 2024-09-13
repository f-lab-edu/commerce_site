package org.example.commerce_site.infrastructure.shipment;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.example.commerce_site.domain.Shipment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ShipmentBulkRepository {
	private final JdbcTemplate jdbcTemplate;

	@Transactional
	public void saveAll(List<Shipment> shipments) {
		String sql = "INSERT INTO shipments (order_id, order_detail_id, address_id, status, created_at) "
			+ "VALUES (?, ?, ?, ?, ?)";

		jdbcTemplate.batchUpdate(sql, shipments, shipments.size(),
			(PreparedStatement ps, Shipment shipment) -> {
				ps.setLong(1, shipment.getOrder().getId());
				ps.setLong(2, shipment.getOrderDetail().getId());
				ps.setLong(3, shipment.getAddress().getId());
				ps.setString(4, shipment.getStatus().name());
				ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now(ZoneOffset.UTC)));
			});
	}
}
