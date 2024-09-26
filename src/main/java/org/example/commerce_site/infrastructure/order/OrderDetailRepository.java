package org.example.commerce_site.infrastructure.order;

import java.util.List;

import org.example.commerce_site.domain.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
	List<OrderDetail> findAllByOrderId(Long orderId);
}
