package org.example.commerce_site.infrastructure.order;

import org.example.commerce_site.application.order.dto.OrderResponseDto;
import org.example.commerce_site.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomOrderRepository {
	Page<OrderResponseDto.Get> getOrders(Pageable pageable, String keyword, Long userId);

	boolean isOrderExists(User user, Long productId);
}
