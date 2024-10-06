package org.example.commerce_site.infrastructure.order;

import java.util.Optional;

import org.example.commerce_site.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	Optional<Order> findByIdAndUserId(Long orderId, Long userId);
}
